package org.xiao.message.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class MessageRedisTemplate extends RedisTemplate<String, String>
{

    public MessageRedisTemplate(MessageRedisConnectionFactory messageRedisConnectionFactory)
    {
        super.setConnectionFactory(messageRedisConnectionFactory);
        super.setKeySerializer(new StringRedisSerializer());
        super.setValueSerializer(new StringRedisSerializer());

    }

    @Override
    public void setKeySerializer(RedisSerializer<?> serializer)
    {

    }

    @Override
    public void setValueSerializer(RedisSerializer<?> serializer)
    {

    }

}
