package org.xiao.message.service;

import org.xiao.message.bean.query.MessageQuery;
import org.xiao.message.document.MessageSourceDocument;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageSourceService
{

    MessageSourceDocument findByMessageId(String messageId);

    /**
     * [简要描述]:分页查找<br/>
     * [详细描述]:<br/>
     *
     * @param query :
     * @return MessageSourceDocument
     * llxiao  2018/10/25 - 14:01
     **/
    Page<MessageSourceDocument> findPage(MessageQuery query);

    /**
     * [简要描述]:ID更新，仅更新状态和IP<br/>
     * [详细描述]:<br/>
     *
     * @param messageId :
     * @param messageSourceDocument : 消息对象
     * @return int
     * llxiao  2018/10/27 - 10:32
     **/
    int updateStatusById(String messageId, MessageSourceDocument messageSourceDocument);

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
     * [简要描述]:日期统计消息总数量<br/>
     * [详细描述]:<br/>
     *
     * @param date : (当天)日期格式yyyy-MM-dd
     * @return int 总数
     * llxiao  2018/11/13 - 15:14
     **/
    Long countByDate(String date);

    /**
     * [简要描述]:状态统计消息总数<br/>
     * [详细描述]:<br/>
     *
     * @param status : 状态，统计未消费、异常、死信数量
     * @return int 总数
     * llxiao  2018/11/13 - 15:16
     **/
    Long countByStatus(Integer status);
}
