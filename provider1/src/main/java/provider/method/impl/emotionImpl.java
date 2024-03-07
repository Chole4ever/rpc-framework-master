package provider.method.impl;

import annotation.rpcServer;
import baseInterface.emotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@rpcServer(interfaceName = emotion.class,version = 1)
public class emotionImpl implements emotion {
    private Logger logger = LoggerFactory.getLogger(emotion.class);

    @Override
    public void presentEmotion(String status) {
        logger.info("this is service from provider1+emotion  "+status);
    }
}
