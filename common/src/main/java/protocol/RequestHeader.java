package protocol;

import codec.RpcSerialization;
import codec.SerializationFactory;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import static constants.ProtocolConstants.MAGIC;

@Data
public class RequestHeader implements Serializable {

        /*
    +---------------------------------------------------------------+
    | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
    +---------------------------------------------------------------+
    | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
    +---------------------------------------------------------------+
    */

    private short magic; // 魔数

    private byte version; // 协议版本号

    private byte serialization; // 序列化算法

    private byte msgType; // 报文类型

    private byte status; // 状态

    private long requestId; // 消息 ID

    private int msgLen; // 数据长度


    public RequestHeader(byte version,byte serialization,byte msgType,byte status,RequestBody requestBody) throws IOException {
        this.magic = MAGIC;
        this.version = version;
        this.serialization = serialization;
        this.msgType = msgType;
        this.status =status;
        this.requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        RpcSerialization serializationUtil = SerializationFactory.getRpcSerialization(serialization);
        byte[] data = serializationUtil.serialize(requestBody);

        this.msgLen = data.length;


    }

}
