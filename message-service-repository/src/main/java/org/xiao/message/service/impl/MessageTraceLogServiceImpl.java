package org.xiao.message.service.impl;

import org.xiao.message.document.MessageTraceLogDocument;
import org.xiao.message.repository.MessageTraceLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.xiao.message.service.MessageTraceLogService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageTraceLogServiceImpl implements MessageTraceLogService
{
    @Autowired
    MessageTraceLogRepository messageTraceLogRepository;

    @Override
    public void add(MessageTraceLogDocument document)
    {
        messageTraceLogRepository.insert(document);
    }

    @Override
    public boolean delete(String traceLogId)
    {
        return messageTraceLogRepository.delete(traceLogId) > 0;
    }

    /**
     * [简要描述]:消息ID查询消息投递记录<br/>
     * [详细描述]:<br/>
     *
     * @param messageId : 消息ID
     * @return java.util.List<MessageTraceLogDocument>
     * llxiao  2018/11/7 - 14:55
     **/
    @Override
    public List<MessageTraceLogDocument> findByMessageId(String messageId)
    {
        List<MessageTraceLogDocument> logDocuments = new ArrayList<>();
        if (StringUtils.isNotBlank(messageId))
        {
            logDocuments = messageTraceLogRepository.findByMessageId(messageId);
        }
        return logDocuments;
    }

    /**
     * [简要描述]:不存在保存，存在更新<br/>
     * [详细描述]:<br/>
     *
     * @param document :
     * llxiao  2018/11/7 - 16:02
     **/
    @Override
    public void saveOrUpdateByMsgOrPosition(MessageTraceLogDocument document)
    {
        this.messageTraceLogRepository.saveOrUpdateByMsgOrPosition(document);
    }
}
