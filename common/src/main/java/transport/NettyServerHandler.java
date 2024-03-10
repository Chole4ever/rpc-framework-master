package transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.RequestContent;

public class NettyServerHandler extends SimpleChannelInboundHandler<RequestContent> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RequestContent requestContent) throws Exception {

    }
}
