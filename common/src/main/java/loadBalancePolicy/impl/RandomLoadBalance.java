package loadBalancePolicy.impl;

import loadBalancePolicy.LoadBalancePolicy;
import loadBalancePolicy.LoadBalancePolicyFactory;

/**
 * @author wangyike
 */
public class RandomLoadBalance implements LoadBalancePolicy {

    @Override
    public int getIndex() {
        return 0;
    }
}
