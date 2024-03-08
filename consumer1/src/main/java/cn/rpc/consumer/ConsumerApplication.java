package cn.rpc.consumer;

import baseInterface.Action;
import cn.rpc.consumer.method.ActionSpi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import proxy.ProxyPool;

@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {

        SpringApplication.run(ConsumerApplication.class);


        ActionSpi action = ProxyPool.getProxy(ActionSpi.class);
        action.testService("hello,fanta sea");

    }
}
