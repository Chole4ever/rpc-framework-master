package provider.method.impl;

import annotation.RpcServer;
import baseInterface.Emotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RpcServer(interfaceName = Emotion.class,version = 1)
public class EmotionImpl implements Emotion {
    private Logger logger = LoggerFactory.getLogger(Emotion.class);

    @Override
    public void presentEmotion(String status) {
        logger.info("this is service from provider1+emotion  "+status);
    }
}
