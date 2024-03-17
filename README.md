# rpc-framework-master
SpringBoot+nacos+netty for rpc framework

实现轻量级RPC框架，使得客户端可以通过网络从远程服务端程序上请求服务 
注册中心部分使用Nacos实现注册、订阅功能
提供了丰富的负载均衡策略：支持轮询、随机、LRU、LFU、一致性HASH
提供多种调用方案，支持同步 Sync、异步 Future、回调 Callback
基于Netty实现多通讯方案：支持 TCP 和 HTTP 两种通讯方式进行服务调用
    1. http方式 类似feign的调用 
    2.TCP使用了netty，自定义消息协议
支持多种序列化协议，比如hession，protobuf，Json
