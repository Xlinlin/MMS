package org.xiao.message.manager.job;

import org.xiao.message.service.MessageStatisticsService;
import org.xiao.message.service.TopicSendStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 09:15
 * @since JDK 1.8
 */
@Slf4j
@Component
public class MessageStatisticsJob
{
    @Autowired
    private MessageStatisticsService messageStatisticsService;

    @Autowired
    private TopicSendStatisticsService topicSendStatisticsService;

    /**
     *[简要描述]:保存前一天的状态数量统计<br/>
     *[详细描述]:<br/>

     * @return void
     * jun.liu  2018/11/13 - 11:35
     **/
    @Scheduled(cron = "0 0 1 * * ?")
    public void countMessageStatus()
    {
        messageStatisticsService.countMessageStatus();
    }

    /**
     * [简要描述]:保存本周每天的消息发送数量<br/>
     * [详细描述]:<br/>
     *
     * @return void
     * jun.liu  2018/11/14 - 17:27
     **/
    @Scheduled(cron = "0 0 1 * * ?")
    public void countWeekMessageSendSum()
    {
        messageStatisticsService.countWeekMessageSendSum();
    }

    /**
     * [简要描述]:保存前几天的topic发送数量<br/>
     * [详细描述]:<br/>
     *
     * @return void
     * jun.liu  2018/11/14 - 17:27
     **/
    @Scheduled(cron = "0 0 1 * * ?")
    public void countTopicSendSum()
    {
        topicSendStatisticsService.countTopicSendSum();
    }
}
