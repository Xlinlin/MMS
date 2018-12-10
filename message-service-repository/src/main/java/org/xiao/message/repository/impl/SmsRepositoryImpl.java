package org.xiao.message.repository.impl;

import org.xiao.message.document.SmsDocument;
import org.xiao.message.repository.SmsRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/30 14:15
 * @since JDK 1.8
 */
@Service
public class SmsRepositoryImpl extends BaseMongoRepositoryImpl<SmsDocument, String> implements SmsRepository
{
    public SmsRepositoryImpl()
    {
        super(SmsDocument.class);
    }

    @Override
    public void batchUpdate(Query query, Update update)
    {
        mongoTemplate.updateMulti(query, update, SmsDocument.class);
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
        return mongoTemplate.count(query, SmsDocument.class);
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
        query.addCriteria(Criteria.where("sendStatus").is(status));
        return mongoTemplate.count(query, SmsDocument.class);
    }
}
