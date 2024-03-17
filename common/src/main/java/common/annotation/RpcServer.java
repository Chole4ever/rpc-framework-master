package common.annotation;

import baseInterface.BaseService;
import common.constants.ProtocolConstants;

import java.lang.annotation.*;

/**
 * @author wangyike
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcServer {

    /** 服务类型 */
    Class<? extends BaseService> interfaceName();

    byte version() default 0x1;

    /** 序列化协议 **/
    byte serialization() default ProtocolConstants.HESSIAN;

}
