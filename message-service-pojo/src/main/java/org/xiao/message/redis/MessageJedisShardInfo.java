package org.xiao.message.redis;

import redis.clients.jedis.JedisShardInfo;

public class MessageJedisShardInfo extends JedisShardInfo
{

    public MessageJedisShardInfo(String host, int port, String name)
    {
        super(host, port, name);
    }

}
