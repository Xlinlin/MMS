package org.xiao.message.redis;

import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class MessageRedisSentinelConfiguration extends RedisSentinelConfiguration
{
    /***
     * host:port,host1:port1,
     */
    private String sentinelListString = "";

    public MessageRedisSentinelConfiguration(String masterName, String host, int port)
    {

        init(masterName, host, port);
    }

    private void init(String masterName, String host, int port)
    {

        //创建一个master对象
        RedisNode redisNode = new RedisNode(host, port);
        redisNode.setName(masterName);

        super.setMaster(redisNode);

    }

    public void setSentinelListString(String sentinelListString)
    {
        this.sentinelListString = sentinelListString;

        if (StringUtils.isEmpty(sentinelListString))
        {
            throw new RuntimeException("哨兵列表不能为空");
        }
        try
        {
            Set<RedisNode> nodes = new HashSet<RedisNode>();
            String[] sentinelList = sentinelListString.split(",");
            for (String sentine : sentinelList)
            {
                String[] temps = sentine.split(":");
                String host = temps[0];
                int port = Integer.valueOf(temps[1]);
                nodes.add(new RedisNode(host, port));
            }
            setSentinels(nodes);
        }
        catch (Exception e)
        {
            throw new RuntimeException("redis配置不正确......." + e.getMessage());
        }
    }

    public String getSentinelListString()
    {
        return sentinelListString;
    }

}
