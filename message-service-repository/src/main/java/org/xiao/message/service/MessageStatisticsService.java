package org.xiao.message.service;

import org.xiao.message.document.MessageWeekSendDocument;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 09:37
 * @since JDK 1.8
 */
@Service
public interface MessageStatisticsService
{
    /**
     *[简要描述]:保存前几天的状态数量统计<br/>
     *[详细描述]:<br/>

     * @return void
     * jun.liu  2018/11/13 - 13:45
     **/
    void countMessageStatus();

    /**
     *[简要描述]:保存前几天消息发送统计<br/>
     *[详细描述]:<br/>

     * @return void
     * jun.liu  2018/11/13 - 13:45
     **/
    void countWeekMessageSendSum();

    /**
     *[简要描述]:获取本周消息发送统计<br/>
     *[详细描述]:<br/>
     * @param date : 当天
     * @return MessageWeekSendDocument
     * jun.liu  2018/11/14 - 10:02
     **/
    List<MessageWeekSendDocument> getMessageWeekSend(Date date);
}
