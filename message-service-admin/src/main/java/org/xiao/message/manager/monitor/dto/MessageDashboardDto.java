package org.xiao.message.manager.monitor.dto;

import lombok.Data;

/**
 * [简要描述]: 试试查询消息展示数据
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/13 16:06
 * @since JDK 1.8
 */
@Data
public class MessageDashboardDto
{
    //当天消息总量
    private Long currentCount;
    //当前未消费消息总量
    private Long unConsumerCount;
    //当前异常消息总量
    private Long abnormalCount;
    //当前死信消息总量
    private Long deadCount;

    // 当天短信总量
    private Long currentSmsCount;
    // 短信待回执总量
    private Long unReceiptSmsCount;
    // 发送失败的
    private Long failSmsCount;
}
