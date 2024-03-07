package proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class proxyPool {

    private static final Map<Class<?>,Object> PROXY_MAP = new ConcurrentHashMap<>();



}
