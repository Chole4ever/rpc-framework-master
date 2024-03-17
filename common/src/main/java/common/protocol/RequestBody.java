package common.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestBody extends BaseMsg implements Serializable {

    //接口名称
    private String interfaceName;
    //接口版本
    private byte version;
    //方法名称
    private String methodName;
    //参数类型
    private Class<?>[] argumentsTypes;
    //具体参数
    private Object[] args;

    public RequestBody(String interfaceName,byte version,String methodName,Class<?>[] argumentsTypes,Object[] args)
    {
        this.interfaceName = interfaceName;
        this.version = version;
        this.methodName = methodName;
        this.argumentsTypes = argumentsTypes;
        this.args = args;
    }
}
