package org.xiao.message.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import org.xiao.message.bean.dto.FeedbackMessageDto;
import org.xiao.message.bean.dto.SendMessageDto;
import org.xiao.message.document.ConsumerDocument;
import org.xiao.message.document.MessageFackbackDocument;
import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.MessageTraceLogDocument;
import org.xiao.message.repository.MessageFackbackRepository;
import org.xiao.message.repository.MessageSourceRepository;
import org.xiao.message.repository.MessageTraceLogRepository;
import org.xiao.message.service.ConsumerService;
import org.xiao.message.service.MessageService;
import org.xiao.message.util.DateUtils;
import org.xiao.message.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService
{

    @Autowired
    MessageSourceRepository messageSourceRepository;

    @Autowired
    MessageFackbackRepository messageFackbackRepository;

    @Autowired
    MessageTraceLogRepository messageTraceLogRepository;

    @Autowired
    ConsumerService consumerService;

    @Override
    public MessageSourceDocument sendMessage(SendMessageDto messageDto)
    {
        if (log.isDebugEnabled())
        {
            log.debug("收到远程发送消息，主题为：{}，消息内容为：（{}）", messageDto.getTopic(), messageDto.getMessage());
        }
        String messageId = UUID.randomUUID().toString();
        int messageStatus = MessageSourceDocument.RECEIVED;

        boolean isError = false;
        List<ConsumerDocument> consumerDocuments = consumerService.findByTopic(messageDto.getTopic());
        if (CollectionUtil.isEmpty(consumerDocuments))
        {
            //消息异常
            messageStatus = MessageSourceDocument.ABNORMAL;
            isError = true;
        }
        else
        {
            consumerFeedback(messageId, consumerDocuments);
        }
        //消息追踪日志
        messageTraceLogReceive(messageId, messageDto.getTopic(), isError);
        //保存并返回消息
        return saveMessage(messageDto, messageId, messageStatus);
    }

    @Override
    public void feedbackMessage(FeedbackMessageDto messageDto)
    {
        receiveMessage(messageDto);

        //        String messageId = messageDto.getMessageId();
        //        String consumerId = messageDto.getConsumerId();

        //        MessageFackbackDocument document = messageFackbackRepository
        //                .findByMessageIdAndConsumerCode(messageId, consumerId);
        //        if (document != null)
        //        {
        //            document.setFeedbackTime(messageDto.getConsumerTime());
        //            document.setConsumerIp(messageDto.getConsumerIp());
        //            messageFackbackRepository.save(document);
        //        }
    }

    @Override
    public MessageSourceDocument findById(String messageId)
    {
        if (StringUtils.isNotBlank(messageId))
        {
            return messageSourceRepository.get(messageId);
        }
        return null;
    }

    // 消息反馈回写
    private void receiveMessage(FeedbackMessageDto messageDto)
    {
        if (log.isInfoEnabled())
        {
            log.info("收到反馈消息，消息ID:{}", messageDto.getMessageId());
        }
        MessageTraceLogDocument traceLogDocument = new MessageTraceLogDocument();

        traceLogDocument.setTopic(messageDto.getTopic());
        traceLogDocument.setMessageId(messageDto.getMessageId());
        traceLogDocument.setTraceLogId(UUID.randomUUID().toString());

        traceLogDocument.setReceiveTime(DateUtils.currentTime());
        traceLogDocument.setReceiveIp(messageDto.getConsumerIp());

        traceLogDocument.setConsumerIp(messageDto.getConsumerIp());
        traceLogDocument.setConsumerTime(DateUtils.currentTime());
        traceLogDocument.setConsumerId(messageDto.getConsumerId());

        traceLogDocument.setPosition(MessageTraceLogDocument.POSITION_CONSUMED);
        // 消费日志
        messageTraceLogRepository.saveOrUpdateByMsgOrPosition(traceLogDocument);

        MessageSourceDocument messageSourceDocument = new MessageSourceDocument();
        messageSourceDocument.setStatus(MessageSourceDocument.CONSUMED);
        messageSourceDocument.setReceiveIp(messageDto.getConsumerIp());
        messageSourceDocument.setReceiveTime(DateUtils.currentTime());
        // 已消费
        messageSourceRepository.updateStatusById(messageDto.getMessageId(), messageSourceDocument);
    }

    // 消息追踪日志
    private void messageTraceLogReceive(String messageId, String topic, boolean isError)
    {
        // 设置消息轨迹已收到消息，入库
        MessageTraceLogDocument traceLogDocument = new MessageTraceLogDocument();
        traceLogDocument.setMessageId(messageId);
        traceLogDocument.setTopic(topic);
        traceLogDocument.setTraceLogId(UUID.randomUUID().toString());
        traceLogDocument.setReceiveIp(NetUtil.getLoacalHost());
        traceLogDocument.setReceiveTime(DateUtils.currentTime());
        traceLogDocument.setPosition(MessageTraceLogDocument.POSITION_RECEIVE);
        if (isError)
        {
            traceLogDocument.setErrorMsg("该主题下暂无消费者!");
        }
        messageTraceLogRepository.save(traceLogDocument);
    }

    //保存消息，并返回
    private MessageSourceDocument saveMessage(SendMessageDto messageDto, String messageId, int status)
    {
        MessageSourceDocument messageSourceDocument = new MessageSourceDocument();
        messageSourceDocument.setBusinessKey(messageDto.getBusinessKey());
        messageSourceDocument.setCreateTime(DateUtils.currentTime());
        messageSourceDocument.setKey(messageDto.getKey());
        messageSourceDocument.setMessage(messageDto.getMessage());
        messageSourceDocument.setMessageId(messageId);
        messageSourceDocument.setTopic(messageDto.getTopic());
        messageSourceDocument.setCreateIp(NetUtil.getLoacalHost());
        messageSourceDocument.setSendCount(0);
        messageSourceDocument.setStatus(status);
        messageSourceRepository.save(messageSourceDocument);
        return messageSourceDocument;
    }

    private void consumerFeedback(String messageId, List<ConsumerDocument> consumerDocuments)
    {
        List<MessageFackbackDocument> fackbackDocuments = new ArrayList<>(consumerDocuments.size());
        MessageFackbackDocument fackbackDocument;
        for (ConsumerDocument document : consumerDocuments)
        {
            fackbackDocument = new MessageFackbackDocument();
            fackbackDocument.setConsumberFackbackId(UUID.randomUUID().toString());
            fackbackDocument.setSendCount(1);
            fackbackDocument.setMessageId(messageId);
            fackbackDocument.setCreateTime(DateUtils.currentTime());
            fackbackDocument.setTopic(document.getTopic());
            //			fackbackDocument.setGroupId(document.getGroupId());
            fackbackDocument.setConsumerCode(document.getConsumerCode());
            fackbackDocuments.add(fackbackDocument);
        }
        messageFackbackRepository.insertAll(fackbackDocuments);
    }
}
