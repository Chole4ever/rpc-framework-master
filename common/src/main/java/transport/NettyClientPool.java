package transport;

import codec.InternalServerMsgCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import org.checkerframework.checker.units.qual.C;
import protocol.BaseMsg;
import protocol.Request;
import protocol.Response;


import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static msg.MsgPool.RESPONSE_MAP;

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

    /*
    异步回调
     */
    public CompletableFuture<Response> getResponse(InetSocketAddress inetSocketAddress, Request request) throws ExecutionException, InterruptedException {
        FixedChannelPool channelPool = CHANNEL_POOL_MAP.get(inetSocketAddress);
        CompletableFuture<Response> resultFuture = new CompletableFuture<>();

        Future<Channel>channelFuture = channelPool.acquire();
        channelFuture.addListener(new GenericFutureListener<Future<Channel>>() {
            @Override
            public void operationComplete(Future<Channel> future) throws Exception {
                RESPONSE_MAP.put(request.getRequestHeader().getRequestId(), resultFuture);
                Channel channel = future.get();
                channel.writeAndFlush(request);
            }
        });
        return resultFuture;
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
                        .addLast("codec",new InternalServerMsgCodec(BaseMsg.class,BaseMsg.class))//入站（解码）+出站（编码）
                        .addLast("handler",new NettyClientHandler());//入站（存入数据response）
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
