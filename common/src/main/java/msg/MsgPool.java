package msg;

import io.netty.channel.ChannelFuture;
import protocol.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class MsgPool {
    public final static Map<Long, CompletableFuture<Response>> RESPONSE_MAP = new ConcurrentHashMap<>();

}
