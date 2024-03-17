package client.loadBalancePolicy;

import java.net.InetSocketAddress;

public interface LoadBalancePolicy {
    public InetSocketAddress getServiceInetSocketAddress(String serviceName);

}
