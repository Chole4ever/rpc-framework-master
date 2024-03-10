package msg;

import protocol.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MsgPool {
    public final static Map<Long, Response> RESPONSE_MAP = new ConcurrentHashMap<>();

    public void putResponseMsg(Response response)
    {

    }
}
