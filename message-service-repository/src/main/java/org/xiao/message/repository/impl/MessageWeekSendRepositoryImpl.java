package org.xiao.message.repository.impl;

import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.MessageWeekSendDocument;
import org.xiao.message.repository.MessageWeekSendRepository;
import org.xiao.message.util.DateUtils;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 17:17
 * @since JDK 1.8
 */
@Service
public class MessageWeekSendRepositoryImpl extends BaseMongoRepositoryImpl<MessageWeekSendDocument,String> implements
        MessageWeekSendRepository
{

    public MessageWeekSendRepositoryImpl()
    {
        super(MessageWeekSendDocument.class);
    }

    @Override
    public MessageWeekSendDocument countWeekMessageSendSum(Integer status,int day)
    {
        Criteria criteria = new Criteria();
        if (status != null)
        {
            criteria.and("status").is(status);
        }
        criteria.andOperator(
                Criteria.where("createTime").regex("^.*"+ DateUtils.getDateBefore(DateUtils.DATE_TO_STRING_SHORT_PATTERN,day)+".*$"));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.count().as("sendSum"),
                Aggregation.project("sendSum")
        );
        AggregationResults<MessageWeekSendDocument> results = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(MessageSourceDocument.class),MessageWeekSendDocument.class);
        MessageWeekSendDocument sendSum = results.getUniqueMappedResult();
        return sendSum;
    }

    @Override
    public void update(Query query, Update update)
    {
        mongoTemplate.updateMulti(query,update,MessageWeekSendDocument.class);
    }
}
