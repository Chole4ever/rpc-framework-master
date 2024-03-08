package service;

import annotation.RpcServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.util.Set;

/*
实现服务的注册
 */

public class ServiceRegister {


    protected String packageName;
    protected String serviceAddress;
    protected int port;
    protected String clusterName;


    @PostConstruct
    public void registerInstance() throws NacosException {
        //

        NamingService namingService =  NamingFactory.createNamingService("127.0.0.1:8848");
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

    protected ServiceRegister(String serviceAddress, int port, String description, String packageName)
    {
        this.serviceAddress = serviceAddress;
        this.port = port;
        this.packageName = packageName;
        this.clusterName = description;
    }

}
