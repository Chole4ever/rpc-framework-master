package service;

import annotation.rpcServer;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/*
实现服务的注册
 */

public class serviceRegister {


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
            rpcServer annotation = clz.getAnnotation(rpcServer.class);
            Class<?> interfaceName = annotation.interfaceName();
            byte version = annotation.version();
            namingService.registerInstance(interfaceName.getName(), serviceAddress, port);
        }
    }
    private Set<Class<?>> getServiceClasses() {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(rpcServer.class);
        return annotatedClasses;
    }

    protected serviceRegister(String serviceAddress,int port,String description,String packageName)
    {
        this.serviceAddress = serviceAddress;
        this.port = port;
        this.packageName = packageName;
        this.clusterName = description;
    }

}
