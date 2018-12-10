package org.xiao.message.repository.impl;

import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.MessageStatusStatisticsDocument;
import org.xiao.message.repository.MessageStatisticsRepository;
import org.xiao.message.util.DateUtils;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 10:14
 * @since JDK 1.8
 */
@Service
public class MessageStatisticsRepositoryImpl extends BaseMongoRepositoryImpl<MessageStatusStatisticsDocument, String>
        implements MessageStatisticsRepository
{
    public MessageStatisticsRepositoryImpl()
    {
        super(MessageStatusStatisticsDocument.class);
    }

    @Override
    public List<MessageStatusStatisticsDocument> countMessageStatus(int day)
    {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createTime").regex("^.*"+ DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,day)+".*$")),
                Aggregation.group("status").count().as("statusSum"),
                Aggregation.project("statusSum","status")
                        .and("status").previousOperation()
        );
        AggregationResults<MessageStatusStatisticsDocument> results = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(MessageSourceDocument.class),MessageStatusStatisticsDocument.class);
        List<MessageStatusStatisticsDocument> list = results.getMappedResults();
        return list;
    }

    @Override
    public void update(Query query, Update update)
    {
        mongoTemplate.updateMulti(query,update,MessageStatusStatisticsDocument.class);
    }
}
