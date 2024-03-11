package codec;

import enums.SerializationTypeEnum;
import codec.impl.HessianSerialization;
import codec.impl.ProtostuffSerialization;

public class SerializationFactory {

    public static RpcSerialization getRpcSerialization(byte serializationType)
    {
        SerializationTypeEnum typeEnum = SerializationTypeEnum.findByType(serializationType);

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
