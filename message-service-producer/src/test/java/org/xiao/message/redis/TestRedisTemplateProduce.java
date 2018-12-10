package org.xiao.message.redis;

import org.xiao.message.constant.Constants;

public class TestRedisTemplateProduce
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
            redisTemplate.convertAndSend(Constants.TOPIC_PRODUCER, "hello===" + i);
            Thread.sleep(100);
        }
        System.exit(0);
        System.out.println("test");
    }
}
