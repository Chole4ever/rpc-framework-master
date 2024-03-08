package protocol;

import java.io.Serializable;
import java.util.UUID;

public class RequestHeader implements Serializable {
    //请求id，唯一标识当前请求
    private Long requestId;
    //request请求包长度
    private int length;


    public RequestHeader(byte[] requestBodyBytes)
    {
        this.requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        this.length = requestBodyBytes.length;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }




}
