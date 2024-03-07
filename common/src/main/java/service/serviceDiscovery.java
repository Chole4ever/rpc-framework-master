package service;

import annotation.rpcClient;
import annotation.rpcServer;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/*
实现服务的发现

 */

public class serviceDiscovery {

    protected String packageName;
    protected final static Map<Class<?>,List<Instance>> SERVICE_MAP = new ConcurrentHashMap<>();

    @Value("${nacos.discovery.server-addr}")
    private String serverList;
    protected serviceDiscovery(String packageName){
        this.packageName = packageName;
    }

    @PostConstruct
    protected void serviceDiscovery() throws NacosException {
        NamingService namingService = NamingFactory.createNamingService(serverList);
        Set<Class<?>> serviceClzs = getServiceName();
        for (Class<?> clz:serviceClzs)
        {
            rpcClient annotation = clz.getAnnotation(rpcClient.class);
            List<Instance> instanceList = namingService.getAllInstances(annotation.interfaceName().getName());
            SERVICE_MAP.put(clz,instanceList);
        }
    }
    private Set<Class<?>> getServiceName() {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(rpcClient.class);
        return annotatedClasses;
    }
}