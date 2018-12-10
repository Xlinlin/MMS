package org.xiao.message.monitor.controller;/**
 * [简要描述]:
 * [详细描述]:
 *
 * @since JDK 1.8
 */

import org.xiao.message.monitor.api.home.api.DashboardStatisticsFeign;
import org.xiao.message.monitor.api.home.bean.dto.MessageDashboardDto;
import org.xiao.message.monitor.api.home.bean.dto.MessageWeekSendDto;
import org.xiao.message.monitor.api.home.bean.dto.TopicSendStatisticsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/14
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/home/home")
public class HomeController
{

    @Autowired
    private DashboardStatisticsFeign dashboardStatisticsFeign;

    @RequestMapping("/realTimeMonitor")
    public MessageDashboardDto realTimeMonitor()
    {
        return dashboardStatisticsFeign.realTimeMonitor();
    }

    @RequestMapping("/messageWeekSend")
    public List<MessageWeekSendDto> listMessageWeekSend(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = sdf.format(date);
        return dashboardStatisticsFeign.listMessageWeekSend(sDate);
    }

    @RequestMapping(value = "/listTopicSendStatistics")
    public List<TopicSendStatisticsDto> listTopicSendStatistics(String date)
    {
        return dashboardStatisticsFeign.listTopicSendStatistics(date);
    }
}
