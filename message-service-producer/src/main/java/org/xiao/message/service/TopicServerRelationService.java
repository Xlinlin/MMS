package org.xiao.message.service;

import org.springframework.kafka.core.KafkaTemplate;

/**
 * [简要描述]: 主题和服务器关系服务
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/5 10:04
 * @since JDK 1.8
 */
public interface TopicServerRelationService
{
    /**
     * [简要描述]:绑定主题和服务器<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 主题
     * @param servers : 服务器信息
     * @return KafkaTemplate
     * llxiao  2018/11/5 - 10:04
     **/
    KafkaTemplate<String, String> bindTopicAndServer(String topic, String servers);

    /**
     * [简要描述]:重试绑定<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 主题信息
     * @return KafkaTemplate
     * llxiao  2018/11/5 - 10:10
     **/
    KafkaTemplate<String, String> retryBindTopicServer(String topic);

    /**
     * [简要描述]:解绑<br/>
     * [详细描述]:<br/>
     *
     * @param topic :
     * @return void
     * llxiao  2018/11/5 - 10:37
     **/
    void unBindTopicServer(String topic);
}
