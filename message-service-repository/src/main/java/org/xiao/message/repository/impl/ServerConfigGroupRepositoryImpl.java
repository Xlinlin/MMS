package org.xiao.message.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import org.xiao.message.document.ServerConfigGroupDocument;
import org.xiao.message.repository.ServerConfigGroupRepository;

import java.util.List;

@Service
public class ServerConfigGroupRepositoryImpl extends BaseMongoRepositoryImpl<ServerConfigGroupDocument, String>
        implements ServerConfigGroupRepository
{

    public ServerConfigGroupRepositoryImpl()
    {
        super(ServerConfigGroupDocument.class);
    }

    @Override
    public ServerConfigGroupDocument findMinUserServerGroup()
    {

        Query query = new Query();
        Sort sort = new Sort(Direction.ASC, "currentUseCount");
        query.with(sort);

        return mongoTemplate.findOne(query, ServerConfigGroupDocument.class);
    }

    @Override
    public void increase(String serverGroupId, boolean isAdd)
    {
        Query query = new Query(Criteria.where("id").is(serverGroupId));
        Update update = new Update();
        if (isAdd)
        {
            //累加
            update.inc("currentUseCount", 1);
        }
        else
        {
            //累减
            mongoTemplate.updateFirst(query, update, ServerConfigGroupDocument.class);
            List<ServerConfigGroupDocument> documents = mongoTemplate.find(query, ServerConfigGroupDocument.class);
            if (CollectionUtil.isNotEmpty(documents))
            {
                ServerConfigGroupDocument document = documents.get(0);
                if (0 < document.getCurrentUseCount())
                {
                    //累减
                    update.inc("currentUseCount", -1);
                }
            }
        }
    }

}
