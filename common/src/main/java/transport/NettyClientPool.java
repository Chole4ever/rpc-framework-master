package transport;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;

import java.net.InetSocketAddress;

public class NettyClientPool {


    private static ChannelPoolMap<InetSocketAddress, FixedChannelPool> CHANNEL_POOL_MAP =null;

    public static Channel getChannel(InetSocketAddress inetSocketAddress)
    {
        return null;
    }



    public static NettyClientPool getInstance(){
        return LazyHolder.INSTANCE;
    }
    private final static class LazyHolder{
        private final static NettyClientPool INSTANCE = new NettyClientPool();
    }
    private NettyClientPool(){

    }
}
