package org.xiao.message.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mongodb.WriteResult;
import org.xiao.message.document.ServerConfigTopicRelationshipDocument;
import org.xiao.message.repository.ServerConfigTopicRelationshipRepository;
import org.xiao.message.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ServerConfigTopicRelationshipRepositoryImpl
        extends BaseMongoRepositoryImpl<ServerConfigTopicRelationshipDocument, String>
        implements ServerConfigTopicRelationshipRepository
{

    public ServerConfigTopicRelationshipRepositoryImpl()
    {
        super(ServerConfigTopicRelationshipDocument.class);
    }

    @Override
    public int bindServerConfigTopic(String topic, String serverIp)
    {

        ServerConfigTopicRelationshipDocument document = new ServerConfigTopicRelationshipDocument();

        document.setCreateTime(DateUtils.currentTime());
        document.setId(UUID.randomUUID().toString());
        document.setServerIp(serverIp);
        document.setTopic(topic);
        document.setStatus(ServerConfigTopicRelationshipDocument.SUCCESS);
        document.setErrorCount(0);
        ServerConfigTopicRelationshipDocument a = save(document);
        if (a != null)
        {
            return 1;
        }
        return 0;
    }

    /**
     * [简要描述]:更新绑定状态<br/>
     * [详细描述]:<br/>
     *
     * @param document : 映射数据
     * @param isError : true异常
     * @return int
     * llxiao  2018/11/5 - 9:28
     **/
    @Override
    public int updateServerConfigTopic(ServerConfigTopicRelationshipDocument document, boolean isError)
    {
        Query query = new Query(Criteria.where("topic").is(document.getTopic()));

        Update update = new Update();
        if (StringUtils.isNotBlank(document.getServerIp()))
        {
            update.set("serverIp", document.getServerIp());
        }
        if (null != document.getStatus() && ServerConfigTopicRelationshipDocument.ERROR == document.getStatus())
        {
            //更新状态
            update.set("status", document.getStatus());
        }

        if (isError)
        {
            update.inc("errorCount", 1);
        }

        //最后一次更新时间
        update.set("updateTime", DateUtils.currentTime());
        mongoTemplate.updateFirst(query, update, ServerConfigTopicRelationshipDocument.class);
        return 0;
    }

    /**
     * [简要描述]:主题查找可用对应的服务器IP信息<br/>
     * [详细描述]:查找正常可用的服务器信息<br/>
     *
     * @param topic : 主题
     * @return java.lang.String
     * llxiao  2018/11/5 - 9:37
     **/
    @Override
    public ServerConfigTopicRelationshipDocument getServerIpByTopic(String topic)
    {
        ServerConfigTopicRelationshipDocument serverConfigTopicRelationshipDocument = null;
        if (StringUtils.isNotBlank(topic))
        {
            Query query = new Query(Criteria.where("topic").is(topic).and("status")
                    .is(ServerConfigTopicRelationshipDocument.SUCCESS));
            List<ServerConfigTopicRelationshipDocument> documents = this.mongoTemplate
                    .find(query, ServerConfigTopicRelationshipDocument.class);
            if (CollectionUtil.isNotEmpty(documents))
            {
                serverConfigTopicRelationshipDocument = documents.get(0);
            }
        }
        return serverConfigTopicRelationshipDocument;
    }

    @Override
    public int unBindByTopic(String topic)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("topic").is(topic));
        WriteResult result = mongoTemplate.remove(query, ServerConfigTopicRelationshipDocument.class);
        return result.getN();
    }

    @Override
    public int unBindByServerIp(String serverIp)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("serverIp").is(serverIp));
        WriteResult result = mongoTemplate.remove(query, ServerConfigTopicRelationshipDocument.class);
        return result.getN();
    }

    @Override
    public List<String> findTopicByServerIp(String serverIp)
    {
        List<String> topics = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("serverIp").is(serverIp));
        List<ServerConfigTopicRelationshipDocument> relationshipDocuments = findByCondition(query);
        if (relationshipDocuments == null || relationshipDocuments.isEmpty())
        {
            return topics;
        }
        for (ServerConfigTopicRelationshipDocument document : relationshipDocuments)
        {
            topics.add(document.getTopic());
        }

        return topics;
    }
}
