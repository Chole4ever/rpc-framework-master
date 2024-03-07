package annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface rpcClient {
    // 服务类型（被暴露的实现类的接口类型）
    Class<?> interfaceName();

    /** 负载服务版本 */
    byte version();

    /** 负载均衡策略 */

}
