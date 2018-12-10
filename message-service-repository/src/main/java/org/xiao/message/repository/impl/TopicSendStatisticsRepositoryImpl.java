package org.xiao.message.repository.impl;

import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.TopicSendStatisticsDocument;
import org.xiao.message.repository.TopicSendStatisticsRepository;
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
 * @version 1.0, 2018/11/14 14:28
 * @since JDK 1.8
 */
@Service
public class TopicSendStatisticsRepositoryImpl extends BaseMongoRepositoryImpl<TopicSendStatisticsDocument, String> implements TopicSendStatisticsRepository
{
    public TopicSendStatisticsRepositoryImpl()
    {
        super(TopicSendStatisticsDocument.class);
    }

    @Override
    public List<TopicSendStatisticsDocument> countTopicSendSum(int day)
    {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createTime").regex("^.*"+ DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,day)+".*$")),
                Aggregation.group("topic").count().as("sendSum"),
                Aggregation.project("topic","sendSum").and("topic").previousOperation()
        );
        AggregationResults<TopicSendStatisticsDocument> results = mongoTemplate.aggregate(aggregation,mongoTemplate.getCollectionName(MessageSourceDocument.class),TopicSendStatisticsDocument.class);
        return results.getMappedResults();
    }

    @Override
    public void update(Query query, Update update)
    {
        mongoTemplate.updateMulti(query,update,TopicSendStatisticsDocument.class);
    }
}
