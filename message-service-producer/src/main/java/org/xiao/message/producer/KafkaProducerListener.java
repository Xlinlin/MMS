package org.xiao.message.producer;

import org.xiao.message.service.MessageSendFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
public class KafkaProducerListener implements ProducerListener<String, String>
{
    private MessageSendFeedbackService messageSendFeedbackService;

    public KafkaProducerListener()
    {
        super();
    }

    public KafkaProducerListener(MessageSendFeedbackService messageSendFeedbackService)
    {
        this.messageSendFeedbackService = messageSendFeedbackService;
    }

    @Override
    public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata)
    {
        log.info("==========kafka发送数据成功（日志开始）==========");
        log.info("----------topic:" + topic);
        log.info("----------partition:" + partition);
        log.info("----------key:" + key);
        log.info("----------value:" + value);
        log.info("----------RecordMetadata:" + recordMetadata);
        log.info("~~~~~~~~~~kafka发送数据成功（日志结束）~~~~~~~~~~");

        if (null != messageSendFeedbackService)
        {
            //发送成功处理
            messageSendFeedbackService.feedbackSendTraceLog(topic, key, null);
            messageSendFeedbackService.feedbackSendMessageStatus(key, false);
        }
    }

    @Override
    public void onError(String topic, Integer partition, String key, String value, Exception exception)
    {
        log.info("==========kafka发送数据错误（日志开始）==========");
        log.info("----------topic:" + topic);
        log.info("----------partition:" + partition);
        log.info("----------key:" + key);
        log.info("----------value:" + value);
        log.info("----------Exception:" + exception);
        log.info("~~~~~~~~~~kafka发送数据错误（日志结束）~~~~~~~~~~");
        log.error("~~~~~~~~~~异常日志：", exception);

        if (null != messageSendFeedbackService)
        {
            //消息发送异常处理
            messageSendFeedbackService.feedbackSendTraceLog(topic, key, exception.getMessage());
            messageSendFeedbackService.feedbackTopicServer(topic, exception.getMessage());
            // 发送失败更新错误消息日志
            messageSendFeedbackService.feedbackSendMessageStatus(key, true);
        }

    }

    @Override
    public boolean isInterestedInSuccess()
    {
        log.info("----------kafkaProducer监听器启动");
        return true;
    }

}
