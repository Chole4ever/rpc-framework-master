package consumer.method;

import annotation.RpcClient;
import baseInterface.Action;
import loadBalancePolicy.impl.RandomLoadBalance;

@RpcClient(interfaceName = Action.class,version = 1,loadBalancePolicy = RandomLoadBalance.class)
public interface ActionSpi extends Action {

}
