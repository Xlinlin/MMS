package org.xiao.message.redis;

public class TestRedisTemplate
{
    public static void main(String[] args)
    {

        RedisConfig config = new RedisConfig();
        MessageJedisPoolConfig poolConfig = config.buildMessageJedisPoolConfig();
        MessageRedisSentinelConfiguration sentinelConfiguration = config.buildMessageRedisSentinelConfiguration();
        MessageRedisConnectionFactory connectionFactory = config
                .buildMessageRedisConnectionFactory(sentinelConfiguration, poolConfig);
        connectionFactory.afterPropertiesSet();
        MessageRedisTemplate redisTemplate = config.buildMessageRedisTemplate(connectionFactory);
        redisTemplate.afterPropertiesSet();
        redisTemplate.opsForValue().set("hello", "nihao");
        String value = redisTemplate.opsForValue().get("hello");
        System.out.println(value);
    }
}
