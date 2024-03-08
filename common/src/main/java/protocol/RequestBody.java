package protocol;

import utils.ByteUtil;

import java.io.Serializable;

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

    public void setProperties(String interfaceName,byte version,String methodName,Class<?>[] argumentsTypes,Object[] args)
    {
        this.interfaceName = interfaceName;
        this.version = version;
        this.methodName = methodName;
        this.argumentsTypes = argumentsTypes;
        this.args = args;
    }
    public byte[] toBytesArray(){
        return ByteUtil.toByteArray(this);
    }
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getArgumentsTypes() {
        return argumentsTypes;
    }

    public void setArgumentsTypes(Class<?>[] argumentsTypes) {
        this.argumentsTypes = argumentsTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }




}
