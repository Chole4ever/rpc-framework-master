package proxy;

import annotation.RpcClient;
import loadBalancePolicy.LoadBalancePolicy;
import loadBalancePolicy.LoadBalancePolicyFactory;
import protocol.RequestBody;
import protocol.RequestContent;
import protocol.RequestHeader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
        //2.wrap in request
        RequestBody requestBody= new RequestBody();
        requestBody.setProperties(annotation.interfaceName().getName(), annotation.version(),methodName,parameterTypes,args);
        RequestHeader requestHeader = new RequestHeader(requestBody.toBytesArray());
        RequestContent requestContent = new RequestContent(requestHeader,requestBody);

        //3.loadBalance execution
        LoadBalancePolicyFactory loadBalancePolicyFactory = LoadBalancePolicyFactory.getInstance();
        LoadBalancePolicy loadBalancePolicy = loadBalancePolicyFactory.getLoadBalancePolicy(annotation.loadBalancePolicy());

        //4.do socket comm by netty


        return null;
    }
}

