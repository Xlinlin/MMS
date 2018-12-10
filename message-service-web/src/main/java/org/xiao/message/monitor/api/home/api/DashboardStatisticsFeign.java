package org.xiao.message.monitor.api.home.api;

import org.xiao.message.monitor.api.home.bean.dto.MessageDashboardDto;
import org.xiao.message.monitor.api.home.bean.dto.MessageWeekSendDto;
import org.xiao.message.monitor.api.home.bean.dto.TopicSendStatisticsDto;
import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/16 09:17
 * @since JDK 1.8
 */
@FeignClient(value = ServiceProduceConstants.MESSAGE_ADMIN)
@RequestMapping("/dashboard")
public interface DashboardStatisticsFeign
{
    /**
     * [简要描述]:实时数据监控<br/>
     * [详细描述]:<br/>
     *
     * @return
     * llxiao  2018/11/16 - 9:15
     **/
    @RequestMapping("/realTimeMonitor")
    MessageDashboardDto realTimeMonitor();

    /**
     * [简要描述]:获取周统计<br/>
     * [详细描述]:<br/>
     *
     * @return
     * jun.liu  2018/11/14 - 10:23
     **/
    @RequestMapping(value = "/listMessageWeekSend")
    List<MessageWeekSendDto> listMessageWeekSend(@RequestParam("date") String date);

    /**
     * [简要描述]:获取前一天的topic发送统计<br/>
     * [详细描述]:<br/>
     *
     * @param date :
     * @return java.util.List
     * jun.liu  2018/11/14 - 17:10
     **/
    @RequestMapping(value = "/listTopicSendStatistics")
    List<TopicSendStatisticsDto> listTopicSendStatistics(@RequestParam("date") String date);
}
