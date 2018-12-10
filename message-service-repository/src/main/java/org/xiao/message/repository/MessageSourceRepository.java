package org.xiao.message.repository;

import org.xiao.message.document.MessageSourceDocument;

import java.util.List;

public interface MessageSourceRepository extends BaseMongoRepository<MessageSourceDocument, String>
{

    /**
     * [简要描述]:消息ID更新消息内容<br/>
     * [详细描述]:<br/>
     *
     * @param messageId : 消息ID
     * @param sourceDocument : 消息内容
     * @return int
     * llxiao  2018/11/1 - 15:16
     **/
    int updateStatusById(String messageId, MessageSourceDocument sourceDocument);

    /**
     * [简要描述]:查询所有未发送的消息<br/>
     * [详细描述]:<br/>
     *
     * @param status 状态
     * @return java.util.List<MessageSourceDocument>
     * llxiao  2018/11/1 - 15:51
     **/
    List<MessageSourceDocument> findUnSend(Integer status);

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
