package org.xiao.message.redis;

import org.xiao.message.constant.Constants;

public class TestRedisQueueProduce
{
    public static void main(String[] args) throws Exception
    {

        RedisConfig config = new RedisConfig();
        MessageJedisPoolConfig poolConfig = config.buildMessageJedisPoolConfig();
        MessageRedisSentinelConfiguration sentinelConfiguration = config.buildMessageRedisSentinelConfiguration();
        MessageRedisConnectionFactory connectionFactory = config
                .buildMessageRedisConnectionFactory(sentinelConfiguration, poolConfig);
        connectionFactory.afterPropertiesSet();
        MessageRedisTemplate redisTemplate = config.buildMessageRedisTemplate(connectionFactory);

        redisTemplate.afterPropertiesSet();

        for (int i = 0; i < 100; i++)
        {
            redisTemplate.opsForList().leftPush(Constants.TOPIC_NOTICE_PRODUCER, "hello===" + i);
        }
        System.exit(0);
        System.out.println("test");
    }
}
