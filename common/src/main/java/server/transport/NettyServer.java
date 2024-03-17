package server.transport;


import common.codec.InternalServerMsgCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import common.protocol.BaseMsg;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Component
public class NettyServer {


    @Value("${local.port}")
    int port;
    @Value("${local.hostname}")
    String hostName;

    @PostConstruct
    public void init()
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(hostName, port))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline()
                                    .addLast("common/codec",new InternalServerMsgCodec(BaseMsg.class,BaseMsg.class))//入站+出站
                                    .addLast("handler",new NettyServerHandler());//入站
                        }


                    });
            ////确保服务器在继续执行之前已成功启动和监听指定端口的常用方法
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}
