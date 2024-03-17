package client.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import common.protocol.Response;

import java.util.concurrent.CompletableFuture;

import static common.msg.MsgPool.RESPONSE_MAP;

/*
　
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Response> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
            if(response==null) {
                return;
            } else {
                CompletableFuture<Response> resultFuture = RESPONSE_MAP.get(response.getHeader().getRequestId());
                resultFuture.complete(response);
                RESPONSE_MAP.remove(response.getHeader().getRequestId());
            }
    }


}
