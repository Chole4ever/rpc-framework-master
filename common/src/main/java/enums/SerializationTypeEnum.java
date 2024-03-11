package enums;

public enum SerializationTypeEnum{
    HESSIAN(0x10),
    PROTOSTUFF(0x20);
    int serializationType;
    SerializationTypeEnum(int serializationType)
    {
        this.serializationType = serializationType;
    }

    public static SerializationTypeEnum findByType(byte serializationType) {

        for(SerializationTypeEnum typeEnum:SerializationTypeEnum.values())
        {
            if(typeEnum.serializationType==serializationType) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }
}
