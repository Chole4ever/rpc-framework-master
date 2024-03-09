package provider.spiService.impl;

import annotation.RpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import baseInterface.Action;


@RpcServer(interfaceName = Action.class,version = 1)
public class ActionImpl implements Action {

    private Logger logger = LoggerFactory.getLogger(ActionImpl.class);

    @Override
    public void testService(String s) {
        logger.info("this is service from provider1+action  "+s);
    }

    @Override
    public void test2(String s) {
        logger.info("this is service from provider1+test2  "+s);
    }


}
