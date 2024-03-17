package provider.spiService.impl;

import common.annotation.RpcServer;
import common.constants.ProtocolConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import baseInterface.service.ActionService;


@RpcServer(interfaceName = ActionService.class, serialization = ProtocolConstants.HESSIAN)
public class ActionImpl implements ActionService {

    private Logger logger = LoggerFactory.getLogger(ActionImpl.class);

    @Override
    public void testService(String s) {
        logger.info("this is service from provider1+action  "+s);
    }

    @Override
    public void testAction(String s) {
        logger.info("this is service from provider1+test2  "+s);
    }


}
