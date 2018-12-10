package org.xiao.message.manager.monitor.dto;

import org.xiao.message.document.MessageWeekSendDocument;
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

    public static MessageWeekSendDto convertToDto(MessageWeekSendDocument document)
    {
        MessageWeekSendDto dto = null;
        if (document!=null)
        {
            dto = new MessageWeekSendDto();
            dto.setSendSum(document.getSendSum());
            dto.setDeadSum(document.getDeadSum());
            dto.setCreateTime(document.getCreateTime());
            dto.setFailSum(document.getFailSum());
            dto.setId(document.getId());
            dto.setStatisticsDate(document.getStatisticsDate());
            dto.setSuccessSum(document.getSuccessSum());
            dto.setUpdateTime(document.getUpdateTime());
        }
        return dto;
    }
}
