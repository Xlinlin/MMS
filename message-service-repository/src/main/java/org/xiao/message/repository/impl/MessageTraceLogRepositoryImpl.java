package org.xiao.message.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import org.xiao.message.document.MessageTraceLogDocument;
import org.xiao.message.repository.MessageTraceLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageTraceLogRepositoryImpl extends BaseMongoRepositoryImpl<MessageTraceLogDocument, String>
        implements MessageTraceLogRepository
{
    public MessageTraceLogRepositoryImpl()
    {
        super(MessageTraceLogDocument.class);
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
        Query mongodbQuery = new Query();
        Criteria criteria = Criteria.where("messageId").is(messageId);
        mongodbQuery.addCriteria(criteria);
        Sort sort = new Sort(Sort.Direction.ASC, "position");
        mongodbQuery.with(sort);
        return mongoTemplate.find(mongodbQuery, MessageTraceLogDocument.class);
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
        Query mongodbQuery = new Query();
        Criteria criteria = Criteria.where("messageId").is(document.getMessageId());
        criteria.and("position").is(document.getPosition());
        mongodbQuery.addCriteria(criteria);
        List<MessageTraceLogDocument> documents = mongoTemplate.find(mongodbQuery, MessageTraceLogDocument.class);
        if (CollectionUtil.isNotEmpty(documents))
        {
            updateTrancLog(document, mongodbQuery);
        }
        else
        {
            mongoTemplate.save(document);
        }
    }

    private void updateTrancLog(MessageTraceLogDocument document, Query mongodbQuery)
    {
        Update update = new Update();
        String receiveIp = document.getReceiveIp();
        if (StringUtils.isNotBlank(receiveIp))
        {
            update.set("receiveIp", receiveIp);
        }
        String receiveTime = document.getReceiveTime();
        if (StringUtils.isNotBlank(receiveTime))
        {
            update.set("receiveTime", receiveTime);
        }
        String sendIp = document.getSendIp();
        if (StringUtils.isNotBlank(sendIp))
        {
            update.set("sendIp", sendIp);
        }
        String sendTime = document.getSendTime();
        if (StringUtils.isNotBlank(sendTime))
        {
            update.set("sendTime", sendTime);
        }
        String consumerTime = document.getConsumerTime();
        if (StringUtils.isNotBlank(consumerTime))
        {
            update.set("consumerTime", consumerTime);
        }
        String consumerIp = document.getConsumerIp();
        if (StringUtils.isNotBlank(consumerIp))
        {
            update.set("consumerIp", consumerIp);
        }
        String consumerId = document.getConsumerId();
        if (StringUtils.isNotBlank(consumerId))
        {
            update.set("consumerId", consumerId);
        }
        String consumerCode = document.getConsumerCode();
        if (StringUtils.isNotBlank(consumerCode))
        {
            update.set("consumerCode", consumerCode);
        }
        String groupId = document.getGroupId();
        if (StringUtils.isNotBlank(groupId))
        {
            update.set("groupId", groupId);
        }
        String errorMsg = document.getErrorMsg();
        if (StringUtils.isNotBlank(errorMsg))
        {
            update.set("errorMsg", errorMsg);
        }
        update.set("position", document.getPosition());
        mongoTemplate.updateFirst(mongodbQuery, update, MessageTraceLogDocument.class);
    }

}
