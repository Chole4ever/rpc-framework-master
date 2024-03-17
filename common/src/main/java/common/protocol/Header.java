package common.protocol;

import common.codec.RpcSerialization;
import common.codec.SerializationFactory;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import static common.constants.ProtocolConstants.MAGIC;

@Data
public class Header implements Serializable {

        /*
    +---------------------------------------------------------------+
    | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
    +---------------------------------------------------------------+
    |        消息 ID 8byte     |      数据长度 4byte     |
    +---------------------------------------------------------------+
    */

    private short magic; // 魔数

    private byte version; // 协议版本号

    private byte serialization; // 序列化算法

    private byte msgType; // 报文类型

    private long requestId; // 消息 ID

    private int msgLen; // 数据长度


    public void setMsgLen(ResponseBody responseBody,byte serialization) throws IOException {
        RpcSerialization serializationUtil = SerializationFactory.getRpcSerialization(serialization);
        byte[] data = serializationUtil.serialize(responseBody);
        this.msgLen = data.length;
    }

    public void setMsgLen(RequestBody requestBody,byte serialization) throws IOException {
        RpcSerialization serializationUtil = SerializationFactory.getRpcSerialization(serialization);
        byte[] data = serializationUtil.serialize(requestBody);
        this.msgLen = data.length;

    }
    public Header(short magic,byte version,byte serialization,byte msgType) throws IOException {
        this.magic = MAGIC;
        this.version = version;
        this.serialization = serialization;
        this.msgType = msgType;
        this.requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());


    }

}
