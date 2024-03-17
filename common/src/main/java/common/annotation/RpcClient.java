package common.annotation;

import baseInterface.BaseService;
import common.constants.ProtocolConstants;
import client.loadBalancePolicy.LoadBalancePolicy;
import client.loadBalancePolicy.impl.RandomLoadBalance;

import java.lang.annotation.*;

/**
 * @author wangyike
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {

    /** 服务类型 */
    Class<? extends BaseService> interfaceName();
    /** 服务版本 */
    byte version() default 0x1;

    /** 负载均衡策略 */
    Class<?extends LoadBalancePolicy> loadBalancePolicy() default RandomLoadBalance.class;

    /** 序列化协议 */
    byte serialization() default ProtocolConstants.HESSIAN;
}
