package client.loadBalancePolicy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
    提供负载均衡的实例工厂
 */
public class LoadBalancePolicyFactory {
    private final static Map<Class<?extends LoadBalancePolicy>,LoadBalancePolicy>
            loadBalanceMap = new ConcurrentHashMap<>();


    public LoadBalancePolicy getLoadBalancePolicy(Class<?extends LoadBalancePolicy> loadBalanceClz)
    {
        return loadBalanceMap.computeIfAbsent(loadBalanceClz,clz->{
            try {
                return clz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }




    private LoadBalancePolicyFactory(){

    }
    /*
    静态内部类加载模式，使用静态内部类来实现单例模式的延迟初始化（Lazy Initialization）
     */
    private static class LazyHolder{
        private static final LoadBalancePolicyFactory INSTANCE = new LoadBalancePolicyFactory();
    }

    public static LoadBalancePolicyFactory getInstance()
    {
        return LazyHolder.INSTANCE;
    }
}
