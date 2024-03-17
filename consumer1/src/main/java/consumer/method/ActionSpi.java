package consumer.method;

import common.annotation.RpcClient;
import baseInterface.service.ActionService;

@RpcClient(interfaceName = ActionService.class)
public interface ActionSpi extends ActionService {

}
