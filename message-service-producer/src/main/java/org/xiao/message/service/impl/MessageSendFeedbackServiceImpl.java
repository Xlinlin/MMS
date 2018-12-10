package org.xiao.message.service.impl;

import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.MessageTraceLogDocument;
import org.xiao.message.document.ServerConfigTopicRelationshipDocument;
import org.xiao.message.producer.KafkaProducerContainer;
import org.xiao.message.service.MessageSendFeedbackService;
import org.xiao.message.service.MessageSourceService;
import org.xiao.message.service.MessageTraceLogService;
import org.xiao.message.service.ServerConfigTopicService;
import org.xiao.message.util.DateUtils;
import org.xiao.message.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * [简要描述]: 消息发送状态反馈服务实现
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/8 09:38
 * @since JDK 1.8
 */
@Service
@Slf4j
public class MessageSendFeedbackServiceImpl implements MessageSendFeedbackService
{

    @Autowired
    private ServerConfigTopicService serverConfigTopicService;

    @Autowired
    private MessageTraceLogService messageTraceLogService;

    @Autowired
    private MessageSourceService messageSourceService;

    /**
     * [简要描述]:消息发送日志反馈<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 主题
     * @param messageId : 消息ID
     * @param errorMassage : 错误消息
     **/
    @Override
    public void feedbackSendTraceLog(String topic, String messageId, String errorMassage)
    {
        MessageTraceLogDocument logDocument = new MessageTraceLogDocument();
        logDocument.setMessageId(messageId);
        logDocument.setTopic(topic);
        logDocument.setTraceLogId(UUID.randomUUID().toString());
        logDocument.setPosition(MessageTraceLogDocument.POSITION_TO_KAFKA);
        logDocument.setConsumerTime(DateUtils.currentTime());
        logDocument.setSendTime(DateUtils.currentTime());
        logDocument.setSendIp(NetUtil.getLoacalHost());
        if (StringUtils.isNotBlank(errorMassage))
        {
            logDocument.setErrorMsg(errorMassage);
        }
        messageTraceLogService.saveOrUpdateByMsgOrPosition(logDocument);
    }

    /**
     * [简要描述]:反馈主题和服务器之间的异常关系<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 主题
     * @param errorMsg : 异常消息
     **/
    @Override
    public void feedbackTopicServer(String topic, String errorMsg)
    {
        ServerConfigTopicRelationshipDocument serverConfigTopicRelationshipDocument = serverConfigTopicService
                .getServerIpByTopic(topic);
        if (null != serverConfigTopicRelationshipDocument)
        {
            Integer errorCount = serverConfigTopicRelationshipDocument.getErrorCount();
            ServerConfigTopicRelationshipDocument tmp = new ServerConfigTopicRelationshipDocument();
            tmp.setTopic(topic);
            if (null != errorCount && ServerConfigTopicRelationshipDocument.MAX_ERROR_COUNT - 1 == errorCount)
            {
                //解绑虚拟机中的绑定关系，标记为异常
                KafkaProducerContainer.removeKafkaTemplate(topic);
                tmp.setStatus(ServerConfigTopicRelationshipDocument.ERROR);
                tmp.setExceptionMsg(errorMsg);
            }
            serverConfigTopicService.updateServerConfigTopic(tmp, true);
        }
    }

    /**
     * [简要描述]:消息发送状态反馈<br/>
     * [详细描述]:<br/>
     *
     * @param messageId : 消息ID
     * @param isError : 是否错误
     **/
    @Override
    public void feedbackSendMessageStatus(String messageId, boolean isError)
    {
        MessageSourceDocument tmp = new MessageSourceDocument();
        if (isError)
        {
            tmp.setStatus(MessageSourceDocument.ABNORMAL);
        }
        else
        {
            tmp.setStatus(MessageSourceDocument.SEND);
        }
        tmp.setProducerIp(NetUtil.getLoacalHost());
        tmp.setProducerTime(DateUtils.currentTime());
        messageSourceService.updateStatusById(messageId, tmp);

        //如果已经达到五次，更新为死信，下次不再发送
        MessageSourceDocument document = messageSourceService.findByMessageId(messageId);
        if (MessageSourceDocument.DEAD_MESSAGE_SEND <= document.getSendCount())
        {
            tmp = new MessageSourceDocument();
            tmp.setStatus(MessageSourceDocument.DEAD);
            messageSourceService.updateStatusById(messageId, tmp);
        }
    }
}
