package proxy;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ProxyPool {
    // 存放代理proxy类
    private static final Map<Class<?>,Object> PROXY_MAP = new ConcurrentHashMap<>();

    public static <T>T getProxy(Class<T> spiClz){

        return (T) PROXY_MAP.computeIfAbsent(spiClz, k-> RpcProxy.getProxy(spiClz));
    }

    private ProxyPool() {

    }
}
