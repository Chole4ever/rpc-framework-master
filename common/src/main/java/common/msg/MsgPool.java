package common.msg;

import common.protocol.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class MsgPool {
    public final static Map<Long, CompletableFuture<Response>> RESPONSE_MAP = new ConcurrentHashMap<>();

}
