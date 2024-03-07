package cn.rpc.consumer.method;

import annotation.rpcClient;
import baseInterface.action;

@rpcClient(interfaceName = action.class,version = 1)
public interface actionSpi extends action {

}
