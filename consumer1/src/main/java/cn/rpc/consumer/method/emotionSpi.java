package cn.rpc.consumer.method;

import annotation.rpcClient;
import baseInterface.emotion;

@rpcClient(interfaceName = emotion.class,version = 1)
public interface emotionSpi extends emotion {
}
