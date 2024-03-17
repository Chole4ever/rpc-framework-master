package consumer.method;

import common.annotation.RpcClient;
import baseInterface.service.EmotionService;

@RpcClient(interfaceName = EmotionService.class)
public interface EmotionSpi extends EmotionService {
}
