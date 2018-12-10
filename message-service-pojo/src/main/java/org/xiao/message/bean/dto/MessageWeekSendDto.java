package org.xiao.message.bean.dto;

import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/14 10:19
 * @since JDK 1.8
 */
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
