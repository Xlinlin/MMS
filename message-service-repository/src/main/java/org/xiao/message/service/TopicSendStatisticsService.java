package org.xiao.message.service;

import org.xiao.message.document.TopicSendStatisticsDocument;

import java.util.Date;
import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/14 14:44
 * @since JDK 1.8
 */
public interface TopicSendStatisticsService
{
    /**
     *[简要描述]:统计前几天topic发送数量<br/>
     *[详细描述]:<br/>

     * @return void
     * jun.liu  2018/11/14 - 14:55
     **/
    void countTopicSendSum();

    /**
     *[简要描述]:获取前一天的topic<br/>
     *[详细描述]:<br/>
     * @param date :
     * @return java.util.List<TopicSendStatisticsDocument>
     * jun.liu  2018/11/14 - 16:43
     **/
    List<TopicSendStatisticsDocument> listTopicSendStatistics(Date date);
}
