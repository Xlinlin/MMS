package org.xiao.message.redis;

import org.xiao.message.consumer.NoticeMessageListener;
import org.xiao.message.consumer.RedisMessageListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class TestRedisTemplateConsumer
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

        RedisMessageListenerContainer container = config
                .createRedisMessageListenerContainer(connectionFactory, new RedisMessageListener()
                {
                    @Override
                    public void onMessage(Message message, byte[] pattern)
                    {
                        byte[] body = message.getBody();
                        String messageId = (String) redisTemplate.getValueSerializer().deserialize(body);
                        System.out.println(" RedisMessageListener==" + messageId);
                    }
                }, new NoticeMessageListener()
                {
                    @Override
                    public void onMessage(Message message, byte[] pattern)
                    {
                        byte[] body = message.getBody();
                        String messageId = (String) redisTemplate.getValueSerializer().deserialize(body);
                        System.out.println(" NoticeMessageListener=================" + messageId);
                    }
                });
        container.afterPropertiesSet();
        container.setTopicSerializer(new StringRedisSerializer());
        container.start();

    }
}
