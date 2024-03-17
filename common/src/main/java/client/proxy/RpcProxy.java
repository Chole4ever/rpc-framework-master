package client.proxy;

import common.annotation.RpcClient;

import java.lang.reflect.Proxy;

public final class RpcProxy {
    public static <T>T getProxy(Class<?> spiClz)
    {
        //检查1 该clz!=null
        if(spiClz==null) {
            return null;
        }
        //检查2,对应的的interfaceClz!=null
        RpcClient annotation = spiClz.getAnnotation(RpcClient.class);
        if(annotation.interfaceName()==null)
        {
            return null;
        }

        ClassLoader classLoader = spiClz.getClassLoader();
        Class<?>[] methodArray = {spiClz};
        //生成代理
        return (T) Proxy.newProxyInstance(classLoader,methodArray,new RpcInvocationHandler(spiClz));
    }


    private RpcProxy(){

    }

}