package enums;

import protocol.BaseMsg;
import protocol.Request;
import protocol.Response;

public enum Msg {

    RESPONSE(1, Response.class),
    REQUEST(2, Request.class);

    private int msgType;
    private Class<?extends BaseMsg> msgClz;

    Msg(int msgType,Class<?extends BaseMsg> msgClz)
    {
        this.msgType = msgType;
        this.msgClz = msgClz;
    }

    public static Class<?extends BaseMsg> findMsgType(int msgType)
    {
        for(Msg msg:Msg.values())
        {
            if(msg.msgType==msgType) {
                return msg.msgClz;
            }
        }
        return RESPONSE.msgClz;
    }

}
