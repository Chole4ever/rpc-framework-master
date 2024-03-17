package common.enums;

public enum Serialization {
    HESSIAN(0x10),
    PROTOSTUFF(0x20);
    int serialization;
    Serialization(int serializationType)
    {
        this.serialization = serializationType;
    }

    public static Serialization findByType(byte serializationType) {

        for(Serialization typeEnum: Serialization.values())
        {
            if(typeEnum.serialization==serializationType) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }
}
