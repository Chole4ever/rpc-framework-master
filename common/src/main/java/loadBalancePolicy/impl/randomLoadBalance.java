package loadBalancePolicy.impl;

import loadBalancePolicy.loadBalancePolicy;
import loadBalancePolicy.loadBalancePolicyFactory;

/**
 * @author wangyike
 */
public class randomLoadBalance extends loadBalancePolicyFactory implements loadBalancePolicy {

    @Override
    public int getIndex() {
        return 0;
    }
}
