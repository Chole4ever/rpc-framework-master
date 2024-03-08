package cn.rpc.consumer.method;

import annotation.RpcClient;
import baseInterface.Emotion;

@RpcClient(interfaceName = Emotion.class,version = 1)
public interface EmotionSpi extends Emotion {
}
