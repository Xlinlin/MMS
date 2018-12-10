package org.xiao.message.manager.monitor;

import org.xiao.message.document.MessageSourceDocument;
import org.xiao.message.document.MessageWeekSendDocument;
import org.xiao.message.document.TopicSendStatisticsDocument;
import org.xiao.message.service.MessageSourceService;
import org.xiao.message.service.MessageStatisticsService;
import org.xiao.message.service.SmsService;
import org.xiao.message.service.TopicSendStatisticsService;
import org.xiao.message.util.DateUtils;
import org.xiao.message.manager.monitor.dto.MessageDashboardDto;
import org.xiao.message.manager.monitor.dto.MessageWeekSendDto;
import org.xiao.message.manager.monitor.dto.TopicSendStatisticsDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/16 09:13
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/dashboard")
@Slf4j
public class DashboardStatisticsApi
{
    private static int SMS_UN_RECEIPT = 1;
    private static int SMS_FAIL = 2;

    @Autowired
    private MessageStatisticsService messageStatisticsService;

    @Autowired
    private MessageSourceService messageSourceService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private TopicSendStatisticsService topicSendStatisticsService;

    /**
     * [简要描述]:实时数据监控<br/>
     * [详细描述]:<br/>
     *
     * @return MessageDashboardDto
     * llxiao  2018/11/16 - 9:15
     **/
    @RequestMapping("/realTimeMonitor")
    public MessageDashboardDto realTimeMonitor()
    {
        MessageDashboardDto messageDashboardDto = new MessageDashboardDto();

        // message
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        messageDashboardDto.setCurrentCount(this.messageSourceService.countByDate(date));
        messageDashboardDto.setAbnormalCount(this.messageSourceService.countByStatus(MessageSourceDocument.ABNORMAL));
        messageDashboardDto.setUnConsumerCount(this.messageSourceService.countByStatus(MessageSourceDocument.SEND));
        messageDashboardDto.setDeadCount(this.messageSourceService.countByStatus(MessageSourceDocument.DEAD));

        //SMS
        messageDashboardDto.setCurrentSmsCount(this.smsService.countByDate(date));
        messageDashboardDto.setUnReceiptSmsCount(this.smsService.countByStatus(SMS_UN_RECEIPT));
        messageDashboardDto.setFailSmsCount(this.smsService.countByStatus(SMS_FAIL));
        return messageDashboardDto;
    }

    /**
     * [简要描述]:获取周统计<br/>
     * [详细描述]:<br/>
     *
     * @return MessageWeekSendDto
     * jun.liu  2018/11/14 - 10:23
     **/
    @RequestMapping(value = "/listMessageWeekSend")
    public List<MessageWeekSendDto> listMessageWeekSend(String date)
    {
        if (date == null)
        {
            log.info("日期不能为空");
            throw new RuntimeException("日期不能为空");
        }
        List<MessageWeekSendDocument> list = messageStatisticsService
                .getMessageWeekSend(DateUtils.stringToDate(date, DateUtils.DATE_TO_STRING_SHORT_PATTERN));
        List<MessageWeekSendDto> dtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list))
        {
            for (MessageWeekSendDocument document : list)
            {
                dtoList.add(MessageWeekSendDto.convertToDto(document));
            }
        }
        return dtoList;
    }

    /**
     * [简要描述]:获取前一天的topic发送统计<br/>
     * [详细描述]:<br/>
     *
     * @param date :
     * @return java.util.List<>
     * jun.liu  2018/11/14 - 17:10
     **/
    @RequestMapping(value = "/listTopicSendStatistics")
    public List<TopicSendStatisticsDto> listTopicSendStatistics(String date)
    {
        if (date == null)
        {
            log.info("日期不能为空");
            throw new RuntimeException("日期不能为空");
        }
        List<TopicSendStatisticsDocument> list = topicSendStatisticsService
                .listTopicSendStatistics(DateUtils.stringToDate(date, DateUtils.DATE_TO_STRING_SHORT_PATTERN));
        List<TopicSendStatisticsDto> newList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list))
        {
            for (TopicSendStatisticsDocument document : list)
            {
                newList.add(TopicSendStatisticsDto.convertToDto(document));
            }
        }
        return newList;
    }
}
