package org.xiao.message.service.impl;

import org.xiao.message.document.ServerConfigTopicRelationshipDocument;
import org.xiao.message.producer.KafkaProducerContainer;
import org.xiao.message.producer.ProducerFactory;
import org.xiao.message.service.MessageSendFeedbackService;
import org.xiao.message.service.ServerConfigTopicService;
import org.xiao.message.service.TopicServerRelationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * [简要描述]: 主题和服务器绑定关系维护
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/5 10:05
 * @since JDK 1.8
 */
@Service
@Slf4j
public class TopicServerRelationServiceImpl implements TopicServerRelationService
{

    @Autowired
    private ServerConfigTopicService serverConfigTopicService;

    @Autowired
    private MessageSendFeedbackService messageSendFeedbackService;

    /**
     * [简要描述]:绑定主题和服务器<br/>
     * [详细描述]:维护内存维护关系<br/>
     *
     * @param topic : 主题
     * @param servers : 服务器信息
     * @return KafkaTemplate
     * llxiao  2018/11/5 - 10:04
     **/
    @Override
    public KafkaTemplate<String, String> bindTopicAndServer(String topic, String servers)
    {
        KafkaTemplate<String, String> template = KafkaProducerContainer.getKafkaTemplate(topic);
        if (template == null)
        {
            template = ProducerFactory.createKafkaTemplate(servers, topic, messageSendFeedbackService);
            // 维护内存中的关系
            KafkaProducerContainer.addKafkaTemplate(topic, template);
            if (log.isInfoEnabled())
            {
                log.info("初始化建立服务器{}和主题{}之间的映射关系!!!!", servers, topic);
            }
            // 绑定主题和服务器
            serverConfigTopicService.bindServerConfigTopic(topic, servers);
        }
        return template;
    }

    /**
     * [简要描述]:重试绑定<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 主题信息
     * @return KafkaTemplate
     * llxiao  2018/11/5 - 10:10
     **/
    @Override
    public KafkaTemplate<String, String> retryBindTopicServer(String topic)
    {
        if (log.isInfoEnabled())
        {
            log.info("尝试重新建立主题{}与对应的服务器之间关系的内存映射!!!!", topic);
        }
        KafkaTemplate<String, String> template = null;
        ServerConfigTopicRelationshipDocument serverConfigTopicRelationshipDocument = serverConfigTopicService
                .getServerIpByTopic(topic);
        if (null != serverConfigTopicRelationshipDocument)
        {
            String serverIp = serverConfigTopicRelationshipDocument.getServerIp();
            if (StringUtils.isNotBlank(serverIp))
            {
                if (log.isInfoEnabled())
                {
                    log.info("重新维护服务器{}与主题{}关系到内存......", serverIp, topic);
                }
                template = ProducerFactory.createKafkaTemplate(serverIp, topic, messageSendFeedbackService);
                //维护内存中的关系
                KafkaProducerContainer.addKafkaTemplate(topic, template);
            }
        }
        else
        {
            log.warn("当前主题{}无对应服务器绑定映射关系!!!!", topic);
        }
        return template;
    }

    /**
     * [简要描述]:解绑<br/>
     * [详细描述]:<br/>
     *
     * @param topic :
     * llxiao  2018/11/5 - 10:37
     **/
    @Override
    public void unBindTopicServer(String topic)
    {
        KafkaProducerContainer.removeKafkaTemplate(topic);
        this.serverConfigTopicService.unBindByTopic(topic);
    }
}
