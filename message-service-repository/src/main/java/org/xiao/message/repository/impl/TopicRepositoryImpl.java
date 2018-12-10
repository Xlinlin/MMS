package org.xiao.message.repository.impl;

import java.util.Arrays;
import java.util.List;

import org.xiao.message.util.DateUtils;
import org.xiao.message.util.NetUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;
import org.xiao.message.document.TopicDocument;
import org.xiao.message.repository.TopicRepository;

@Service
public class TopicRepositoryImpl extends BaseMongoRepositoryImpl<TopicDocument, String> implements TopicRepository
{

    public TopicRepositoryImpl()
    {
        super(TopicDocument.class);
    }

    public List<TopicDocument> findStopTopics()
    {
        Criteria criteria = new Criteria();
        criteria.and("status").in(TopicDocument.STOP);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, TopicDocument.class);
    }

    @Override
    public int updateTopicStatus(List<String> topics, int status, String serverGroupId)
    {
        Criteria criteria = new Criteria();
        criteria.and("topic").in(topics);
        Update update = new Update();
        if (TopicDocument.START == status)
        {
            update.set("startTime", DateUtils.currentTime());
            update.set("createIp", NetUtil.getLoacalHost());
            update.set("serverGroupId", serverGroupId);
        }
        else if (TopicDocument.STOP == status)
        {
            update.set("stopTime", DateUtils.currentTime());
            update.set("createIp", "");
            update.set("serverGroupId", "");
        }
        update.set("status", status);
        WriteResult result = mongoTemplate.updateMulti(new Query(criteria), update, TopicDocument.class);
        return result.getN();
    }

    @Override
    public TopicDocument findByTopic(String topic)
    {
        Criteria criteria = new Criteria();
        criteria.and("topic").is(topic);
        Query query = new Query(criteria);
        List<TopicDocument> documents = mongoTemplate.find(query, TopicDocument.class);
        if (documents == null || documents.isEmpty())
        {
            return null;
        }
        return documents.get(0);
    }

    @Override
    public void increase(String topic)
    {
        Criteria criteria = new Criteria();
        criteria.and("topic").is(topic);
        Query query = new Query(criteria);
        Update update = new Update();
        update.inc("sendCount", 1);
        mongoTemplate.updateFirst(query, update, TopicDocument.class);
    }

    @Override
    public int updateTopicStatus(String topic, int status, String serverGroupId)
    {
        List<String> topics = Arrays.asList(topic);
        return updateTopicStatus(topics, status, serverGroupId);
    }

}
