package cn.rpc.consumer.config;

import com.alibaba.nacos.api.exception.NacosException;
import service.ServiceDiscovery;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryConfig extends ServiceDiscovery {
    protected DiscoveryConfig() throws NacosException {
        super("cn.rpc.consumer");
    }
}
