package org.xiao.message.repository.impl;

import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.repository.MessageSourceRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageSourceRepositoryImpl extends BaseMongoRepositoryImpl<MessageSourceDocument, String>
        implements MessageSourceRepository
{

    public MessageSourceRepositoryImpl()
    {
        super(MessageSourceDocument.class);
    }

    /**
     * [简要描述]:更新状态<br/>
     * [详细描述]:<br/>
     *
     * @param messageId : 消息ID
     * @param sourceDocument : 消息内容
     * @return int
     * llxiao  2018/11/1 - 15:08
     **/
    @Override
    public int updateStatusById(String messageId, MessageSourceDocument sourceDocument)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(messageId));
        Update update = new Update();
        Integer status = sourceDocument.getStatus();
        if (null != status)
        {
            update.set("status", sourceDocument.getStatus());
            //更新发送次数
            if (MessageSourceDocument.DEAD != status && MessageSourceDocument.CONSUMED != status)
            {
                update.inc("sendCount", 1);
            }
        }

        if (StringUtils.isNotBlank(sourceDocument.getReceiveIp()))
        {
            //更新ip
            update.set("receiveIp", sourceDocument.getReceiveIp());
        }
        if (StringUtils.isNotBlank(sourceDocument.getReceiveTime()))
        {
            update.set("receiveTime", sourceDocument.getReceiveTime());
        }

        if (StringUtils.isNotBlank(sourceDocument.getProducerIp()))
        {
            update.set("producerIp", sourceDocument.getProducerIp());
        }
        if (StringUtils.isNotBlank(sourceDocument.getProducerTime()))
        {
            update.set("producerTime", sourceDocument.getProducerTime());
        }
        return mongoTemplate.updateFirst(query, update, MessageSourceDocument.class).getN();
    }

    /**
     * [简要描述]:查询所有未发送的消息<br/>
     * [详细描述]:<br/>
     *
     * @return java.util.List<MessageSourceDocument>
     * llxiao  2018/11/1 - 15:51
     **/
    @Override
    public List<MessageSourceDocument> findUnSend(Integer status)
    {
        Query query = new Query();
        // 需要查询 已接收、异常、已发送未消费的数据
        query.addCriteria(Criteria.where("status").is(status));
        return mongoTemplate.find(query, MessageSourceDocument.class);
    }

    /**
     * [简要描述]:按天统计总数<br/>
     * [详细描述]:<br/>
     *
     * @param date :
     * @return java.lang.Long
     * llxiao  2018/11/13 - 15:19
     **/
    @Override
    public Long countByDate(String date)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").regex(date + "*"));
        return mongoTemplate.count(query, MessageSourceDocument.class);
    }

    /**
     * [简要描述]:按状态统计总数<br/>
     * [详细描述]:<br/>
     *
     * @param status :
     * @return java.lang.Long
     * llxiao  2018/11/13 - 15:19
     **/
    @Override
    public Long countByStatus(Integer status)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(status));
        return mongoTemplate.count(query, MessageSourceDocument.class);
    }
}
