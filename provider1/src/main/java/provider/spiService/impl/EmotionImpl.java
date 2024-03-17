package provider.spiService.impl;

import common.annotation.RpcServer;
import baseInterface.service.EmotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RpcServer(interfaceName = EmotionService.class)
public class EmotionImpl implements EmotionService {
    private Logger logger = LoggerFactory.getLogger(EmotionService.class);

    @Override
    public void presentEmotion(String status) {
        logger.info("this is service from provider1+emotion  "+status);
    }
}
