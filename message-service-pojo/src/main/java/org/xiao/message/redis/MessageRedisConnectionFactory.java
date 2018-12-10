package org.xiao.message.redis;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class MessageRedisConnectionFactory extends JedisConnectionFactory
{

    public MessageRedisConnectionFactory(MessageRedisSentinelConfiguration sentinelConfiguration,
            MessageJedisPoolConfig poolConfig)
    {
        super(sentinelConfiguration, poolConfig);
    }

    //单节点模式
    public MessageRedisConnectionFactory(MessageJedisPoolConfig poolConfig, String hostName, int port)
    {
        this.setPoolConfig(poolConfig);
        this.setHostName(hostName);
        this.setPort(port);
    }

    @Override
    public void setPassword(String password)
    {
        super.setPassword(password);
    }

}
