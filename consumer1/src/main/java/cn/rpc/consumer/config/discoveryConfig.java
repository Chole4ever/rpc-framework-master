package cn.rpc.consumer.config;

import com.alibaba.nacos.api.exception.NacosException;
import service.serviceDiscovery;
import org.springframework.stereotype.Component;

@Component
public class discoveryConfig extends serviceDiscovery {
    protected discoveryConfig() throws NacosException {
        super("cn.rpc.consumer");

    }

}
