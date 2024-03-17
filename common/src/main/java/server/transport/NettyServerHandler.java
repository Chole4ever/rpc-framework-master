package server.transport;

import baseInterface.BaseService;
import common.constants.ProtocolConstants;
import common.protocol.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.services.ServiceRegister;

import java.lang.reflect.Method;

public class NettyServerHandler extends SimpleChannelInboundHandler<Request> {



    Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        //处理服务逻辑，调用服务类反射，返回结果
        logger.info("This is channel read on server" + request.getHeader());
        logger.info("This is channel read by body on server" + request.getRequestBody());

        Header header = request.getHeader();
        RequestBody requestBody = request.getRequestBody();

        String serviceName = requestBody.getInterfaceName();
        String methodName = requestBody.getMethodName();
        BaseService service = (BaseService) ServiceRegister.getService(serviceName).newInstance();

        try {
            Method method = service.getClass().getMethod(methodName);
            Object object = method.invoke(service, requestBody.getArgs());

            header.setMsgType(ProtocolConstants.RESPONSE);
            header.setMsgLen(requestBody,header.getSerialization());

            ResponseBody responseBody = new ResponseBody(null,object);

            Response response = new Response(header,responseBody);
            channelHandlerContext.channel().writeAndFlush(response);

        } catch (Exception e)
        {
            e.printStackTrace();
        }



    }
}
