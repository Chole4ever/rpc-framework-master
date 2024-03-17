package common.codec;

import common.codec.impl.HessianSerialization;
import common.codec.impl.ProtostuffSerialization;
import common.enums.Serialization;

public class SerializationFactory {

    public static RpcSerialization getRpcSerialization(byte serializationType)
    {
        Serialization typeEnum = Serialization.findByType(serializationType);

        switch (typeEnum) {
            case HESSIAN:
                return new HessianSerialization();
            case PROTOSTUFF:
                return new ProtostuffSerialization();
            default:
                throw new IllegalArgumentException("serialization type is illegal, " + serializationType);

        }
    }

}
