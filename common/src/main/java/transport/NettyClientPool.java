package transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import msg.MsgPool;
import protocol.RequestContent;
import protocol.Response;


import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

public class NettyClientPool {

    private final static int MAX_CONNECTION =4;
    /**
     * 允许将特定的key映射到ChannelPool，可以获取匹配的ChannelPool,如果不存在则会创建一个新的对象
     * 即：根据不同的服务器地址初始化ChannelPoolMap
     */
    private ChannelPoolMap<InetSocketAddress, FixedChannelPool> CHANNEL_POOL_MAP ;

    /**
     * 线程工作组
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    /**
     * 创建客户端的启动对象 bootstrap
     */
    private Bootstrap bootstrap = new Bootstrap();

    public Response getResponse(InetSocketAddress inetSocketAddress, RequestContent requestContent) throws ExecutionException, InterruptedException {
        FixedChannelPool channelPool = CHANNEL_POOL_MAP.get(inetSocketAddress);
        channelPool.acquire().addListener((GenericFutureListener<? extends Future<? super Channel>>) new GenericFutureListener<ChannelFuture>() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess())
                {
                    Channel channel = channelFuture.channel();
                    ChannelFuture channelFutureMSG = channel.writeAndFlush(requestContent);
                    channelFutureMSG.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if(channelFuture.isSuccess())
                            {

                            }
                        }
                    });
                }
            }
        });
        return null;
    }


    public static NettyClientPool getInstance(){
        return LazyHolder.INSTANCE;
    }
    private final static class LazyHolder{
        private final static NettyClientPool INSTANCE = new NettyClientPool();
    }
    private NettyClientPool(){
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline channelPipeline = nioSocketChannel.pipeline();
                channelPipeline
                        .addLast(new InternalServerMsgCodec())
                        .addLast(new NettyClientHandler());
            }
        });
        CHANNEL_POOL_MAP = new AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(InetSocketAddress inetSocketAddress) {
                return new FixedChannelPool(bootstrap.remoteAddress(inetSocketAddress), new AbstractChannelPoolHandler() {
                    @Override
                    public void channelCreated(Channel channel) throws Exception {
                        //Called once a new Channel is created in the ChannelPool.
                        // This method will be called by the EventLoop of the Channel.
                    }
                },MAX_CONNECTION);
            }
            ///Called once a new ChannelPool needs to be created as non exists yet for the key.
        };
    }
}
