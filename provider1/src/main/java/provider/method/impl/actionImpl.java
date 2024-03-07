package provider.method.impl;

import annotation.rpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import baseInterface.action;
import org.springframework.beans.factory.annotation.Value;
import service.serviceRegister;


@rpcServer(interfaceName = action.class,version = 1)
public class actionImpl implements action {

    private Logger logger = LoggerFactory.getLogger(actionImpl.class);

    @Override
    public void testService(String s) {
        logger.info("this is service from provider1+action  "+s);
    }



}
