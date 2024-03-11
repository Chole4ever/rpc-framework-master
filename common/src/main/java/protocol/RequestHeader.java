package protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

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


    public RequestHeader(byte[] requestBodyBytes)
    {
        this.requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        this.msgLen = requestBodyBytes.length;
    }

}
