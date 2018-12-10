package org.xiao.message.monitor.api.home.bean.dto;

import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author xmli
 * @version 1.0, 2018/11/14 18:01
 * @since JDK 1.8
 */
@Data
public class TopicSendStatisticsDto
{
    private String id;

    private String topic;

    private String statisticsDate;

    private Integer sendSum;

    private String createTime;

    private String updateTime;
}
