package org.xiao.message.service.impl;

import org.xiao.message.bean.query.MessageQuery;
import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.repository.MessageSourceRepository;
import org.xiao.message.service.MessageSourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageSourceServiceImpl implements MessageSourceService
{

    @Autowired
    MessageSourceRepository messageSourceRepository;

    @Override
    public MessageSourceDocument findByMessageId(String messageId)
    {
        return messageSourceRepository.get(messageId);
    }

    /**
     * [简要描述]:分页查找<br/>
     * [详细描述]:<br/>
     *
     * @param query :
     * @return MessageSourceDocument
     * llxiao  2018/10/25 - 14:01
     **/
    @Override
    public Page<MessageSourceDocument> findPage(MessageQuery query)
    {
        if (null != query)
        {
            //查询条件
            Query mongodbQuery = setQueryCriteria(query);

            //创建时间排序，默认降序
            Sort.Direction direction = query.isDirection() ? Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sort = new Sort(direction, "createTime");
            PageRequest pageRequest = new PageRequest(query.getPageNum(), query.getPageSize());
            return messageSourceRepository.queryByConditionPage(mongodbQuery, pageRequest, sort);
        }
        return new PageImpl<>(new ArrayList<>());
    }

    /**
     * [简要描述]:ID更新状态<br/>
     * [详细描述]:<br/>
     *
     * @param messageId :
     * @param sourceDocument : 更新内容
     * @return int
     * llxiao  2018/10/27 - 10:32
     **/
    @Override
    public int updateStatusById(String messageId, MessageSourceDocument sourceDocument)
    {
        return messageSourceRepository.updateStatusById(messageId, sourceDocument);
    }

    /**
     * [简要描述]:查询所有未发送的消息<br/>
     * [详细描述]:<br/>
     *
     * @param status 状态
     * @return java.util.List<MessageSourceDocument>
     * llxiao  2018/11/1 - 15:51
     **/
    @Override
    public List<MessageSourceDocument> findUnSend(Integer status)
    {
        return messageSourceRepository.findUnSend(status);
    }

    /**
     * [简要描述]:日期统计消息总数量<br/>
     * [详细描述]:<br/>
     *
     * @param date : (当天)日期格式yyyy-MM-dd
     * @return int 总数
     * llxiao  2018/11/13 - 15:14
     **/
    @Override
    public Long countByDate(String date)
    {
        return messageSourceRepository.countByDate(date);
    }

    /**
     * [简要描述]:状态统计消息总数<br/>
     * [详细描述]:<br/>
     *
     * @param status : 状态，统计未消费、异常、死信数量
     * @return int 总数
     * llxiao  2018/11/13 - 15:16
     **/
    @Override
    public Long countByStatus(Integer status)
    {
        return messageSourceRepository.countByStatus(status);
    }

    private Query setQueryCriteria(MessageQuery query)
    {
        Query mongodbQuery = new Query();
        String topic = query.getTopic();
        if (StringUtils.isNotBlank(topic))
        {
            mongodbQuery.addCriteria(Criteria.where("topic").is(topic));
        }

        String messageId = query.getMessageId();
        if (StringUtils.isNotBlank(messageId))
        {
            mongodbQuery.addCriteria(Criteria.where("messageId").is(messageId));
        }

        Integer status = query.getStatus();
        if (null != status)
        {
            mongodbQuery.addCriteria(Criteria.where("status").is(status));
        }

        else
        {
            mongodbQuery.addCriteria(Criteria.where("status").ne(MessageSourceDocument.DEAD));
        }

        String key = query.getKey();
        if (StringUtils.isNotBlank(topic) && StringUtils.isNotBlank(key))
        {
            mongodbQuery.addCriteria(Criteria.where("key").is(key));
        }
        String businessKey = query.getBusinessKey();
        if (StringUtils.isNotBlank(topic) && StringUtils.isNotBlank(businessKey))
        {
            mongodbQuery.addCriteria(Criteria.where("businessKey").is(businessKey));
        }

        String startTime = query.getStartTime();
        String endTime = query.getEndTime();
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime))
        {
            mongodbQuery.addCriteria(Criteria.where("createTime").gte(startTime).lte(endTime));
        }
        else
        {
            if (StringUtils.isNotBlank(startTime))
            {
                mongodbQuery.addCriteria(Criteria.where("createTime").gte(startTime));
            }
            if (StringUtils.isNotBlank(endTime))
            {
                mongodbQuery.addCriteria(Criteria.where("createTime").lte(endTime));
            }
        }
        return mongodbQuery;
    }

}
