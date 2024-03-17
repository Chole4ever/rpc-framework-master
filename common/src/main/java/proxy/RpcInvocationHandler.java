package proxy;

import annotation.RpcClient;
import loadBalancePolicy.LoadBalancePolicy;
import loadBalancePolicy.LoadBalancePolicyFactory;
import protocol.RequestBody;
import protocol.Request;
import protocol.RequestHeader;
import protocol.Response;
import transport.NettyClientPool;

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
        Class<?>[] parameterTypes = method.getParameterTypes();

        //2.wrap in a request
        RequestBody requestBody= new RequestBody();
        requestBody.setProperties(annotation.interfaceName().getName(),methodName,parameterTypes,args);

        RequestHeader requestHeader = new RequestHeader(annotation.version(), (byte) 1, (byte) 2, (byte) 1,requestBody);
        Request request = new Request(requestHeader,requestBody);

        //3.loadBalance execution
        LoadBalancePolicyFactory loadBalancePolicyFactory = LoadBalancePolicyFactory.getInstance();
        LoadBalancePolicy loadBalancePolicy = loadBalancePolicyFactory.getLoadBalancePolicy(annotation.loadBalancePolicy());
        InetSocketAddress serviceInetSocketAddress = loadBalancePolicy.getServiceInetSocketAddress(serviceName);


        //4.do socket comm by netty
        NettyClientPool nettyClientPool = NettyClientPool.getInstance();
        CompletableFuture<Response> responseCompletableFuture = nettyClientPool.getResponse(serviceInetSocketAddress, request);

        return responseCompletableFuture.get(1000, TimeUnit.SECONDS);
    }
}

