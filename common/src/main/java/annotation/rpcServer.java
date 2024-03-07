package annotation;

import java.lang.annotation.*;

/**
 * @author wangyike
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface rpcServer {

    // 服务类型（被暴露的实现类的接口类型）
    Class<?> interfaceName();

    byte version() default 0;
}
