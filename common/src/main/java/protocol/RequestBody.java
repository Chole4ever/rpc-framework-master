package protocol;

import lombok.Data;
import utils.ByteUtil;

import java.io.Serializable;

import static constants.ProtocolConstants.VERSION;

@Data
public class RequestBody implements Serializable {
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

    public void setProperties(String interfaceName,String methodName,Class<?>[] argumentsTypes,Object[] args)
    {
        this.interfaceName = interfaceName;
        this.version = VERSION;
        this.methodName = methodName;
        this.argumentsTypes = argumentsTypes;
        this.args = args;
    }


}
