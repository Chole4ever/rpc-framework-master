package protocol;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Response extends BaseMsg{


    // 表示对该 requestId 的请求进行响应
    private Long requestId;

    private Exception exception;

    private Object result;
}
