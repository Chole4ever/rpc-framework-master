package client.proxy;

import common.annotation.RpcClient;
import common.constants.ProtocolConstants;
import client.loadBalancePolicy.LoadBalancePolicy;
import client.loadBalancePolicy.LoadBalancePolicyFactory;
import common.protocol.RequestBody;
import common.protocol.Request;
import common.protocol.Header;
import common.protocol.Response;
import client.transport.NettyClientPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RpcInvocationHandler implements InvocationHandler {

    private final Class<?> spiClz;
    public RpcInvocationHandler(Class<?> spiClz) {
        this.spiClz = spiClz;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //1. retrieve information of interface
        RpcClient annotation = spiClz.getAnnotation(RpcClient.class);

        String serviceName = annotation.interfaceName().getName();
        String methodName = method.getName();
        byte serialization = annotation.serialization();
        byte version = annotation.version();
        Class<?>[] parameterTypes = method.getParameterTypes();

        //2.wrap in a request
        RequestBody requestBody =
                new RequestBody(serviceName,version,methodName,parameterTypes,args);

        Header header =
                new Header(ProtocolConstants.MAGIC, ProtocolConstants.VERSION,serialization,ProtocolConstants.REQUEST);

        header.setMsgLen(requestBody,serialization);

        Request request = new Request(header,requestBody);

        //3.loadBalance execution
        LoadBalancePolicyFactory loadBalancePolicyFactory = LoadBalancePolicyFactory.getInstance();
        LoadBalancePolicy loadBalancePolicy = loadBalancePolicyFactory.getLoadBalancePolicy(annotation.loadBalancePolicy());
        InetSocketAddress serviceInetSocketAddress = loadBalancePolicy.getServiceInetSocketAddress(serviceName);

        //4.do socket comm with netty
        NettyClientPool nettyClientPool = NettyClientPool.getInstance();
        CompletableFuture<Response> responseCompletableFuture = nettyClientPool.getResponse(serviceInetSocketAddress, request);

        return responseCompletableFuture.get(1000, TimeUnit.SECONDS);
    }
}

