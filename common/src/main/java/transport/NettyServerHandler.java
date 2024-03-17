package transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.Request;

public class NettyServerHandler extends SimpleChannelInboundHandler<Request> {
    Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        //处理服务逻辑，调用服务类反射，返回结果
        logger.info("This is channel read on server"+request.getRequestHeader());
        logger.info("This is channel read by body on server"+request.getRequestBody());
    }
}
