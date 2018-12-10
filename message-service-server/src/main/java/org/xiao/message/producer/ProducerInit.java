package org.xiao.message.producer;

import cn.hutool.core.collection.CollectionUtil;
import org.xiao.message.constant.Constants;
import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.TopicDocument;
import org.xiao.message.redis.MessageRedisTemplate;
import org.xiao.message.redis.RedisLock;
import org.xiao.message.service.MessageSourceService;
import org.xiao.message.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProducerInit implements InitializingBean
{

    @Autowired
    private MessageRedisTemplate redisTemplate;

    @Autowired
    private TopicService topicService;

    @Autowired
    private MessageSourceService messageSourceService;

    @Override
    public void afterPropertiesSet()
    {
        checkAndStartTopic();
    }

    @Scheduled(fixedRate = 5000)  //每隔5秒执行一次定时创建未启用的创建主题任务
    public void checkAndStartTopic()
    {
        List<TopicDocument> documents = topicService.findStopTopics();
        if (CollectionUtils.isEmpty(documents))
        {
            return;
        }

        for (TopicDocument document : documents)
        {
            if (null != document && StringUtils.isNotBlank(document.getTopic()))
            {
                log.info("发送通知创建主题==" + document.getTopic());
                redisTemplate.convertAndSend(Constants.TOPIC_NOTICE_PRODUCER,
                        Constants.TOPIC_NOTICE_START + document.getTopic());
                // 发送次数+1
                topicService.increase(document.getTopic());
                if (document.getSendCount() + 1 == TopicDocument.RETRY_COUNT)
                {
                    log.error("发送创建主题请求已达到最大限制{}次数，标记为停止创建请求!", TopicDocument.RETRY_COUNT);
                    topicService
                            .updateTopicStatus(document.getTopic(), TopicDocument.STOP, document.getServerGroupId());
                }
            }
        }
    }

    /**
     * [简要描述]:每隔10S定时发送已接收未发送的消息<br/>
     * [详细描述]:<br/>
     * <p>
     * llxiao  2018/11/1 - 15:40
     **/
    @Scheduled(fixedRate = 10000)
    public void checkReceiveMessage()
    {

        List<MessageSourceDocument> messageSourceDocuments = messageSourceService
                .findUnSend(MessageSourceDocument.RECEIVED);
        if (CollectionUtil.isNotEmpty(messageSourceDocuments))
        {
            if (log.isInfoEnabled())
            {
                log.info("----每隔10S定时读取已接收，待发送的消息，共{}个未发送消息", messageSourceDocuments.size());
            }
        }
        reSendMessage(messageSourceDocuments);
    }

    /**
     * [简要描述]:每隔15S定时发送已发送未消费的消息<br/>
     * [详细描述]:<br/>
     * <p>
     * llxiao  2018/11/1 - 15:40
     **/
    //@Scheduled(fixedRate = 15000)
    public void checkUnSendMessage()
    {
        List<MessageSourceDocument> messageSourceDocuments = messageSourceService
                .findUnSend(MessageSourceDocument.SEND);
        if (CollectionUtil.isNotEmpty(messageSourceDocuments))
        {
            if (log.isInfoEnabled())
            {
                log.info("----每隔15S定时读取已发送，未接收的消息，共{}个未发送消息", messageSourceDocuments.size());
            }
        }
        reSendMessage(messageSourceDocuments);
    }

    /**
     * [简要描述]:15S定时发送异常消息<br/>
     * [详细描述]:<br/>
     * <p>
     * llxiao  2018/11/1 - 16:03
     **/
    @Scheduled(fixedRate = 15000)
    public void checkAbnormalMessage()
    {
        List<MessageSourceDocument> messageSourceDocuments = messageSourceService
                .findUnSend(MessageSourceDocument.ABNORMAL);
        if (CollectionUtil.isNotEmpty(messageSourceDocuments))
        {
            if (log.isInfoEnabled())
            {
                log.info("----每隔15S定时读取有异常的消息，共{}个未发送消息", messageSourceDocuments.size());
            }
        }
        reSendMessage(messageSourceDocuments);
    }

    private void reSendMessage(List<MessageSourceDocument> messageSourceDocuments)
    {
        if (CollectionUtil.isNotEmpty(messageSourceDocuments))
        {
            for (MessageSourceDocument messageSourceDocument : messageSourceDocuments)
            {
                safeResendMessage(messageSourceDocument.getMessageId());
            }
        }
    }

    // 安全发送消息
    private void safeResendMessage(String messageId)
    {
        // 使用方法，创建RedisLock对象
        RedisLock lock = new RedisLock(redisTemplate, "lock_message_" + messageId);
        try
        {
            if (lock.lock())
            {
                //发送消息
                redisTemplate.convertAndSend(Constants.TOPIC_PRODUCER, messageId);
            }
        }
        catch (Exception e)
        {
            log.error("定时发送下消息失败，出现未知异常", e);
        }
        finally
        {
            lock.unlock();
        }

    }
}
