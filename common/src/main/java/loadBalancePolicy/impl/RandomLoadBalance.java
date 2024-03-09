package loadBalancePolicy.impl;

import loadBalancePolicy.LoadBalancePolicy;
import loadBalancePolicy.LoadBalancePolicyFactory;
import service.ServiceDiscovery;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wangyike
 */
public class RandomLoadBalance implements LoadBalancePolicy {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public int getClientIndex(int size) {
        if(size <= 0){
            return -1;
        }

        return size != 1
                // random bound 上界不包括 需要+1
                ? random.nextInt(size)
                : 0;
    }

    @Override
    public InetSocketAddress getServiceInetSocketAddress(String serviceName) {
        List<InetSocketAddress> inetSocketAddressList = ServiceDiscovery.getinetSocketAddressList(serviceName);
        int index = getClientIndex(inetSocketAddressList.size());
        return inetSocketAddressList.get(index);
    }
}
