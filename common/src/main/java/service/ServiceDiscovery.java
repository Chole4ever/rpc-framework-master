package service;

import annotation.RpcClient;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/*
实现服务的发现

 */

@Component
public class ServiceDiscovery {

    @Value("${spi.package.name}")
    protected String packageName;
    protected final static Map<String,List<Instance>> SERVICE_MAP = new ConcurrentHashMap<>();

    @Value("${nacos.discovery.server-addr}")
    private String serverList;



    public static List<InetSocketAddress> getinetSocketAddressList(String serviceName)
    {
        List<Instance> instanceList = SERVICE_MAP.get(serviceName);
        List<InetSocketAddress> inetSocketAddressList
                = instanceList.stream()
                .map(instance->new InetSocketAddress(instance.getIp(), instance.getPort()))
                .collect(Collectors.toList());

        return inetSocketAddressList;
    }

    @PostConstruct
    protected void serviceDiscovery() throws NacosException {
        NamingService namingService = NamingFactory.createNamingService(serverList);
        Set<Class<?>> serviceClzs = getServiceName();
        for (Class<?> clz:serviceClzs)
        {
            RpcClient annotation = clz.getAnnotation(RpcClient.class);
            List<Instance> instanceList = namingService.getAllInstances(annotation.interfaceName().getName());
            SERVICE_MAP.put(annotation.interfaceName().getName(),instanceList);
        }
    }
    private Set<Class<?>> getServiceName() {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(RpcClient.class);
        return annotatedClasses;
    }
}