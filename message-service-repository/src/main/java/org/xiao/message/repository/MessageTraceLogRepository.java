package org.xiao.message.repository;

import org.xiao.message.document.MessageTraceLogDocument;

import java.util.List;

public interface MessageTraceLogRepository extends BaseMongoRepository<MessageTraceLogDocument, String>
{

    /**
     * [简要描述]:消息ID查询消息投递记录<br/>
     * [详细描述]:<br/>
     *
     * @param messageId : 消息ID
     * @return java.util.List<MessageTraceLogDocument>
     * llxiao  2018/11/7 - 14:55
     **/
    List<MessageTraceLogDocument> findByMessageId(String messageId);

    /**
     * [简要描述]:不存在保存，存在更新<br/>
     * [详细描述]:<br/>
     *
     * @param document :
     * llxiao  2018/11/7 - 16:02
     **/
    void saveOrUpdateByMsgOrPosition(MessageTraceLogDocument document);

}
