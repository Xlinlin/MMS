package org.xiao.message.repository;

import org.xiao.message.document.MessageStatusStatisticsDocument;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 10:13
 * @since JDK 1.8
 */
public interface MessageStatisticsRepository extends BaseMongoRepository<MessageStatusStatisticsDocument, String>
{
    /**
     *[简要描述]:统计每日状态数量<br/>
     *[详细描述]:<br/> 

     * @return java.util.List<MessageStatusStatisticsDocument>
     * jun.liu  2018/11/13 - 10:36
     **/
    List<MessageStatusStatisticsDocument> countMessageStatus(int day);

    /**
     *[简要描述]:更新<br/>
     *[详细描述]:<br/>
     * @return void
     * jun.liu  2018/11/13 - 14:37
     **/
    void update(Query query, Update update);
}
