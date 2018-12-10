package org.xiao.message.consumer;

import com.google.gson.Gson;
import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.MessageTraceLogDocument;
import org.xiao.message.producer.KafkaProducerContainer;
import org.xiao.message.redis.MessageRedisTemplate;
import org.xiao.message.service.MessageSendFeedbackService;
import org.xiao.message.service.MessageSourceService;
import org.xiao.message.service.MessageTraceLogService;
import org.xiao.message.service.TopicServerRelationService;
import org.xiao.message.util.DateUtils;
import org.xiao.message.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@Slf4j
public class RedisMessageListener implements MessageListener
{
    @Autowired
    private MessageRedisTemplate redisTemplate;

    @Autowired
    private MessageSourceService messageSourceService;

    @Autowired
    private MessageTraceLogService messageTraceLogService;

    @Autowired
    private TopicServerRelationService topicServerRelationService;

    @Autowired
    private MessageSendFeedbackService messageSendFeedbackService;

    @Override
    public void onMessage(Message message, byte[] pattern)
    {
        byte[] body = message.getBody();
        String messageId = (String) redisTemplate.getValueSerializer().deserialize(body);
        MessageSourceDocument document = messageSourceService.findByMessageId(messageId);
        //消息存在，未消费的数据
        if (document != null && document.getStatus() != MessageSourceDocument.CONSUMED)
        {
            if (log.isDebugEnabled())
            {
                log.debug("接收到消息{}发送到消息队列Broker-topic:{}", document.getMessage(), document.getTopic());
            }
            String topic = document.getTopic();

            //消息已从Redis接收
            redisReceive(topic, messageId);

            KafkaTemplate<String, String> template = KafkaProducerContainer.getKafkaTemplate(topic);
            //重新建立绑定关系
            if (null == template)
            {
                template = topicServerRelationService.retryBindTopicServer(topic);
            }
            if (template == null)
            {
                log.error("不能发送消息到消息服务器,当前主题下暂无绑定消息发送者");
                messageSendFeedbackService.feedbackSendMessageStatus(messageId, true);
            }
            else
            {
                //发消息到kafka
                sendKafka(document, template);
            }
        }
        else
        {
            log.error("消息ID为{}的消息不存在!!!!!");
        }
    }

    // 消息发送到kafka
    private void sendKafka(MessageSourceDocument document, KafkaTemplate<String, String> template)
    {
        KafkaDeliveryMessageDto messageDto = new KafkaDeliveryMessageDto();
        messageDto.setMessageBody(document.getMessage());
        messageDto.setMessageId(document.getMessageId());
        template.send(document.getTopic(), document.getMessageId(), new Gson().toJson(messageDto));
    }

    // 消息已从redis接收
    private void redisReceive(String topic, String messageId)
    {
        MessageTraceLogDocument logDocument = new MessageTraceLogDocument();
        logDocument.setMessageId(messageId);
        logDocument.setTopic(topic);
        logDocument.setTraceLogId(UUID.randomUUID().toString());
        logDocument.setPosition(MessageTraceLogDocument.POSITION_FOR_REDIS);
        logDocument.setReceiveIp(NetUtil.getLoacalHost());
        logDocument.setReceiveTime(DateUtils.currentTime());
        messageTraceLogService.saveOrUpdateByMsgOrPosition(logDocument);
    }
}
