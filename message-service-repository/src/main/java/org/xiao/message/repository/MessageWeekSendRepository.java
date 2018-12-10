package org.xiao.message.repository;

import org.xiao.message.document.MessageWeekSendDocument;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 17:16
 * @since JDK 1.8
 */
public interface MessageWeekSendRepository extends BaseMongoRepository<MessageWeekSendDocument,String>
{
    /**
     *[简要描述]:获取本周发送总数<br/>
     *[详细描述]:<br/>

     * @return MessageWeekSendDocument
     * jun.liu  2018/11/13 - 16:15
     **/
    MessageWeekSendDocument countWeekMessageSendSum(Integer status,int day);

    /**
     *[简要描述]:更新<br/>
     *[详细描述]:<br/>
     * @param query :
     * @param update :
     * @return void
     * jun.liu  2018/11/14 - 9:49
     **/
    void update(Query query, Update update);
}
