package service;

import annotation.RpcServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/*
实现服务的注册
 */

@Component
public class ServiceRegister {

    @Value("${serviceRegister.packageName}")
    protected String packageName;
    @Value("${serviceRegister.serviceAddress}")
    protected String serviceAddress;
    @Value("${serviceRegister.port}")
    protected int port;
    @Value("${nacos.discovery.server-addr}")
    protected String serverList;
    @PostConstruct
    public void registerInstance() throws NacosException {
        //

        NamingService namingService =  NamingFactory.createNamingService(serverList);
        Set<Class<?>> serviceClasses = getServiceClasses();
        for(Class<?> clz:serviceClasses)
        {
            RpcServer annotation = clz.getAnnotation(RpcServer.class);
            Class<?> interfaceName = annotation.interfaceName();
            byte version = annotation.version();
            namingService.registerInstance(interfaceName.getName(), serviceAddress, port);
        }
    }
    private Set<Class<?>> getServiceClasses() {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(RpcServer.class);
        return annotatedClasses;
    }

}
