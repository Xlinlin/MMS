package org.xiao.message.service;

import org.xiao.message.consumer.BaseKafkaConsumerServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/9 16:02
 * @since JDK 1.8
 */
@Service
@Slf4j
public class DemoMessageConsumer extends BaseKafkaConsumerServer
{
    // 可自己写死ip地址，也可以使用主题自动查询对应服务器地址
    //@Value("${kafka.server.ip:\"\"}")
    //private String kafkaServerIp;

    @Value("${group.id}")
    private String groupId;

    @Value("${topic}")
    private String topic;

    @Override
    public String getConsumerId()
    {
        return groupId;
    }

    @Override
    public String getTopic()
    {
        return topic;
    }

    //    @Override
    //    public String getKafkaServerIp()
    //    {
    //        return kafkaServerIp;
    //    }

    @Override
    public MessageListener getMessageListener()
    {
        return this;
    }

    @Override
    public void consumerMessage(String message)
    {
        log.info("-----messageBody=" + message);
    }
}
