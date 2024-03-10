package transport;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Response;

import static msg.MsgPool.RESPONSE_MAP;

/*
　
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Response> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
            if(response==null) {
                return;
            } else {
                RESPONSE_MAP.put(response.getRequestId(),response);
            }
    }


}
