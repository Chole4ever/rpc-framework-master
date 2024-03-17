package consumer;

import consumer.method.ActionSpi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import client.proxy.ProxyPool;

@SpringBootApplication(scanBasePackages = {"client","common"})
public class ConsumerApplication {
    public static void main(String[] args) {

        SpringApplication.run(ConsumerApplication.class);
        ActionSpi action = ProxyPool.getProxy(ActionSpi.class);
        action.testService("hello,Fanta sea,This is thread-"+Thread.currentThread().getName());

        /**
        //多线程模拟并发请求
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        for(int i=0;i<10;i++)
        {
            threadPool.submit(()->{
                ActionSpi action = ProxyPool.getProxy(ActionSpi.class);
                action.testService("hello,Fanta sea,This is thread-"+Thread.currentThread().getName());
            });
        }
         **/
    }
}
