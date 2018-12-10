package org.xiao.message.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiao.message.bean.dto.FeedbackMessageDto;
import org.xiao.message.bean.dto.SendMessageDto;
import org.xiao.message.constant.Constants;
import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.MessageTraceLogDocument;
import org.xiao.message.redis.MessageRedisTemplate;
import org.xiao.message.repository.MessageTraceLogRepository;
import org.xiao.message.service.MessageService;
import org.xiao.message.util.DateUtils;
import org.xiao.message.util.NetUtil;

import java.util.UUID;

@RestController
@RequestMapping("/api/message")
@Slf4j
public class MessageApi
{
    private static final String SUCCESS = "1";
    private static final String ERROR = "0";

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRedisTemplate redisTemplate;

    @Autowired
    MessageTraceLogRepository messageTraceLogRepository;

    @PostMapping(path = "send")
    public String sendMessage(@RequestBody SendMessageDto messageDto)
    {
        if (log.isDebugEnabled())
        {
            log.debug("收到发送消息请求，消息主题：{}，消息内容{}", messageDto.getTopic(), messageDto.getMessage());
        }
        MessageSourceDocument document = messageService.sendMessage(messageDto);
        String messageId = document.getMessageId();
        // 不为异常状态
        if (MessageSourceDocument.ABNORMAL != document.getStatus())
        {
            sendToRedis(document);
            redisTemplate.convertAndSend(Constants.TOPIC_PRODUCER, messageId);
        }
        return messageId;
    }

    /**
     * [简要描述]:消息重复发送<br/>
     * [详细描述]:<br/>
     *
     * @param messageId :
     * @return java.lang.String
     * llxiao  2018/11/7 - 10:37
     **/
    @RequestMapping("/resend")
    public String resendMessage(String messageId)
    {
        if (StringUtils.isNotBlank(messageId))
        {
            MessageSourceDocument document = messageService.findById(messageId);
            if (null != document)
            {
                sendToRedis(document);
                redisTemplate.convertAndSend(Constants.TOPIC_PRODUCER, messageId);
                return SUCCESS;
            }
            else
            {
                log.warn("重复发送消息失败，消息ID为{}的消息不存在!", messageId);
            }
        }
        return ERROR;
    }

    @PostMapping(path = "feedback")
    public String feedbackMessage(@RequestBody FeedbackMessageDto messageDto)
    {
        messageService.feedbackMessage(messageDto);
        return SUCCESS;
    }

    // 设置消息轨迹已发送到redis
    private void sendToRedis(MessageSourceDocument document)
    {
        MessageTraceLogDocument traceLogDocument = new MessageTraceLogDocument();
        traceLogDocument.setMessageId(document.getMessageId());
        traceLogDocument.setTopic(document.getTopic());
        traceLogDocument.setTraceLogId(UUID.randomUUID().toString());
        traceLogDocument.setSendIp(NetUtil.getLoacalHost());
        traceLogDocument.setSendTime(DateUtils.currentTime());
        traceLogDocument.setPosition(MessageTraceLogDocument.POSITION_TO_REDIS);
        messageTraceLogRepository.saveOrUpdateByMsgOrPosition(traceLogDocument);
    }
}
