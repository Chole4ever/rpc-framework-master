package annotation;

import loadBalancePolicy.LoadBalancePolicy;
import loadBalancePolicy.impl.RandomLoadBalance;
import org.checkerframework.checker.units.qual.C;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {
    // 服务类型（被暴露的实现类的接口类型）
    Class<?> interfaceName();

    /** 负载服务版本 */
    Class<?extends LoadBalancePolicy> loadBalancePolicy() default RandomLoadBalance.class;
    byte version();

    /** 负载均衡策略 */

}
