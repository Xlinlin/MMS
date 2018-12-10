package org.xiao.message.service.impl;

import org.xiao.message.bean.query.TopicQuery;
import org.xiao.message.document.ConsumerDocument;
import org.xiao.message.document.TopicDocument;
import org.xiao.message.repository.ServerConfigTopicRelationshipRepository;
import org.xiao.message.repository.TopicRepository;
import org.xiao.message.service.ConsumerService;
import org.xiao.message.service.ServerConfigService;
import org.xiao.message.service.TopicService;
import org.xiao.message.util.DateUtils;
import org.xiao.message.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService
{

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    ServerConfigService serverConfigService;
    @Autowired
    ServerConfigTopicRelationshipRepository serverConfigTopicRelationshipRepository;
    @Autowired
    ConsumerService consumerService;

    @Override
    public List<TopicDocument> findStopTopics()
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is(TopicDocument.UN_START));
        return topicRepository.findByCondition(query);
    }

    @Override
    public void updateTopicStatus(String topic, int status, String serverGroupId)
    {
        topicRepository.updateTopicStatus(topic, status, serverGroupId);
        boolean isAdd = false;
        if (status == TopicDocument.START)
        {
            isAdd = true;
        }
        serverConfigService.increase(serverGroupId, isAdd);
    }

    @Override
    public void updateTopicStatus(List<String> topics, int status, String serverGroupId)
    {
        topicRepository.updateTopicStatus(topics, status, serverGroupId);
    }

    @Override
    public int saveTopic(TopicDocument document)
    {
        TopicDocument topicDocument = findByTopic(document.getTopic());
        if (topicDocument != null)
        {
            log.error("主题{}已存在，不能重复创建", topicDocument.getTopic());
            return 0;
        }
        document.setId(UUID.randomUUID().toString());
        document.setCreateTime(DateUtils.currentTime());
        document.setCreateIp(NetUtil.getLoacalHost());
        return null != topicRepository.save(document) ? 1 : 0;
    }

    @Override
    public TopicDocument findByTopic(String topic)
    {
        return topicRepository.findByTopic(topic);
    }

    @Override
    public TopicDocument removeTopic(String id)
    {
        TopicDocument document = topicRepository.get(id);
        int c = topicRepository.delete(id);
        if (c > 0 && document != null)
        {
            List<ConsumerDocument> consumers = consumerService.findByTopic(document.getTopic());
            if (!CollectionUtils.isEmpty(consumers))
            {
                for (ConsumerDocument consumerDocument : consumers)
                {
                    int a = consumerService.remove(consumerDocument.getConsumerId());
                    if (a <= 0)
                    {
                        throw new RuntimeException("删除topic绑定消费者失败");
                    }
                }
            }
            return document;
        }
        return null;
    }

    @Override
    public Page findPage(TopicQuery topicQuery)
    {
        Query query = new Query();
        String topic = topicQuery.getTopic();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(topic))
        {
            criteria.and("topic").is(topic);
        }

        if (StringUtils.isNotBlank(topicQuery.getStartTime()) && StringUtils.isNotBlank(topicQuery.getStopTime()))
        {
            criteria.andOperator(Criteria.where("startTime").gte(topicQuery.getStartTime()), Criteria.where("stopTime")
                    .lte(topicQuery.getStopTime() + " 23:59:59"));
        }
        else
        {
            if (StringUtils.isNotBlank(topicQuery.getStartTime()))
            {
                criteria.and("startTime").gte(topicQuery.getStartTime());
            }
            if (StringUtils.isNotBlank(topicQuery.getStopTime()))
            {
                criteria.and("stopTime").lte(topicQuery.getStopTime());
            }
        }
        Integer status = topicQuery.getStatus();
        if (null != status)
        {
            criteria.and("status").is(status);
        }

        query.addCriteria(criteria);

        PageRequest pageRequest = new PageRequest(topicQuery.getPageNum(), topicQuery.getPageSize());
        return topicRepository.queryByConditionPage(query, pageRequest);
    }

    @Override
    public TopicDocument getById(String topicId)
    {
        return topicRepository.get(topicId);
    }

    /**
     * [简要描述]:主题发起创建请求自增<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 自增
     * llxiao  2018/11/8 - 19:58
     **/
    @Override
    public void increase(String topic)
    {
        topicRepository.increase(topic);
    }

}
