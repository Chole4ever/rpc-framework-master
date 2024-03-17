package common.enums;

import common.protocol.*;

public enum Msg {

    RESPONSE(1, ResponseBody.class),
    REQUEST(2, RequestBody.class);

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
