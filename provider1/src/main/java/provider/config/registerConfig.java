package provider.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import service.serviceRegister;

@Component
public class registerConfig extends serviceRegister {


    private Logger logger = LoggerFactory.getLogger(registerConfig.class);
    /*
        扫描模块内带有rpcServer注解的服务，向nacos注册
     */

    private registerConfig(@Value("${serviceRegister.serviceAddress}") String serviceAddress,
                           @Value("${serviceRegister.clusterName}") String clusterName,
                           @Value("${serviceRegister.port}") int port)
    {
        super(serviceAddress,port,clusterName,"provider.method");
        logger.info("this is service registration");
    }



}
