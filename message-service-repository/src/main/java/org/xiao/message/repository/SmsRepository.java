package org.xiao.message.repository;

import org.xiao.message.document.SmsDocument;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/30 14:07
 * @since JDK 1.8
 */
public interface SmsRepository extends BaseMongoRepository<SmsDocument, String>
{
    void batchUpdate(Query query, Update update);

    /**
     * [简要描述]:按天统计总数<br/>
     * [详细描述]:<br/>
     *
     * @param date :
     * @return java.lang.Long
     * llxiao  2018/11/13 - 15:19
     **/
    Long countByDate(String date);

    /**
     * [简要描述]:按状态统计总数<br/>
     * [详细描述]:<br/>
     *
     * @param status :
     * @return java.lang.Long
     * llxiao  2018/11/13 - 15:19
     **/
    Long countByStatus(Integer status);
}
