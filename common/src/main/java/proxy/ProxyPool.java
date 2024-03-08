package proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ProxyPool {
    // 存放代理proxy类
    private static final Map<Class<?>,Object> PROXY_MAP = new ConcurrentHashMap<>();

    public static <T>T getProxy(Class<T> spiClz){

        Object proxy = PROXY_MAP.computeIfAbsent(spiClz,(clz)->RpcProxy.getProxy(spiClz));
        return (T) proxy;

    }

    private ProxyPool() {

    }
}
