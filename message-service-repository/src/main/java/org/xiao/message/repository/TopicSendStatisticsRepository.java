package org.xiao.message.repository;

import org.xiao.message.document.TopicSendStatisticsDocument;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/14 14:27
 * @since JDK 1.8
 */
public interface TopicSendStatisticsRepository extends BaseMongoRepository<TopicSendStatisticsDocument, String>
{
    /**
     *[简要描述]:<br/>
     *[详细描述]:<br/> 
     * @param day :
     * @return java.util.List<TopicSendStatisticsDocument>
     * jun.liu  2018/11/14 - 15:13
     **/
    List<TopicSendStatisticsDocument> countTopicSendSum(int day);

    /**
     *[简要描述]:更新<br/>
     *[详细描述]:<br/>
     * @param query :
     * @param update :
     * @return void
     * jun.liu  2018/11/14 - 16:16
     **/
    void update(Query query, Update update);
}
