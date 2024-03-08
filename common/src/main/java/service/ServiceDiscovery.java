package service;

import annotation.RpcClient;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
实现服务的发现

 */

public class ServiceDiscovery {

    protected String packageName;
    protected final static Map<Class<?>,List<Instance>> SERVICE_MAP = new ConcurrentHashMap<>();

    @Value("${nacos.discovery.server-addr}")
    private String serverList;
    protected ServiceDiscovery(String packageName){
        this.packageName = packageName;
    }

    @PostConstruct
    protected void serviceDiscovery() throws NacosException {
        NamingService namingService = NamingFactory.createNamingService(serverList);
        Set<Class<?>> serviceClzs = getServiceName();
        for (Class<?> clz:serviceClzs)
        {
            RpcClient annotation = clz.getAnnotation(RpcClient.class);
            List<Instance> instanceList = namingService.getAllInstances(annotation.interfaceName().getName());
            SERVICE_MAP.put(clz,instanceList);
        }
    }
    private Set<Class<?>> getServiceName() {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(RpcClient.class);
        return annotatedClasses;
    }
}