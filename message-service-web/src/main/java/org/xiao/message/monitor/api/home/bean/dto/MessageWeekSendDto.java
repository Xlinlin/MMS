package org.xiao.message.monitor.api.home.bean.dto;


import lombok.Data;

@Data
public class MessageWeekSendDto
{
    private String id;

    private Integer sendSum;

    private Integer successSum;

    private Integer failSum;

    private Integer deadSum;

    private String createTime;

    private String statisticsDate;

    private String updateTime;

    private String weekFirstDate;

    private String weekLastDate;

}
