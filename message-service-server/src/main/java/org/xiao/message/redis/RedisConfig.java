package org.xiao.message.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(MessageRedisCache.class)
@Configuration
public class RedisConfig
{

    @Value("${repository.redis.master.name:NONE}")
    private String masterName = "master";
    @Value("${repository.redis.master.port:26379}")
    private int port = 26389;
    @Value("${repository.redis.master.host:NONE}")
    private String host = "192.168.206.200";

    @Value("${repository.redis.sentinel.list:NONE}")
    private String sentinelListString = "192.168.206.200:26389";

    @Value("${repository.redis.sentinel.password}")
    private String password = "123456";

    @Value("${repository.redis.master.databases}")
    private int databases = 0;

    @Value("${repository.redis.host.name:NONE}")
    private String hostName;

    @Value("${repository.redis.host.port:6379}")
    private int hostPort;

    /***
     * jedis连接池配置 -
     * @return
     */
    @Bean
    public MessageJedisPoolConfig buildMessageJedisPoolConfig()
    {
        return new MessageJedisPoolConfig();
    }

    /***
     * 哨兵配置
     * @return
     */
    @Bean
    public MessageRedisSentinelConfiguration buildMessageRedisSentinelConfiguration()
    {
        if (!masterName.equals("NONE"))
        {
            MessageRedisSentinelConfiguration configuration = new MessageRedisSentinelConfiguration(masterName, host, port);
            configuration.setSentinelListString(sentinelListString);
            return configuration;
        }
        return null;
    }

    /***
     * redis工厂配置
     * @return
     */
    @Bean
    public MessageRedisConnectionFactory buildMessageRedisConnectionFactory(
            MessageRedisSentinelConfiguration sentinelConfiguration, MessageJedisPoolConfig poolConfig)
    {
        MessageRedisConnectionFactory factory;
        if (null != sentinelConfiguration)
        {
            //redis工厂配置-sentinel模式
            factory = new MessageRedisConnectionFactory(sentinelConfiguration, poolConfig);
        }
        else
        {
            //redis工厂配置-单节点模式
            factory = new MessageRedisConnectionFactory(poolConfig, hostName, hostPort);
        }
        factory.setPassword(password);
        factory.setDatabase(databases);
        return factory;
    }

    /***
     * redis模板配置
     * @return
     */
    @Bean
    public MessageRedisTemplate buildMessageRedisTemplate(MessageRedisConnectionFactory topScoreRedisConnectionFactory)
    {
        return new MessageRedisTemplate(topScoreRedisConnectionFactory);
    }

}
