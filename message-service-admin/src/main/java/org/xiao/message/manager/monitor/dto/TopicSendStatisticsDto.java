package org.xiao.message.manager.monitor.dto;

import org.xiao.message.document.TopicSendStatisticsDocument;
import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/14 16:39
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

    public static TopicSendStatisticsDto convertToDto(TopicSendStatisticsDocument document)
    {
        TopicSendStatisticsDto dto = null;
        if (document!=null)
        {
            dto = new TopicSendStatisticsDto();
            dto.setCreateTime(document.getCreateTime());
            dto.setId(document.getId());
            dto.setSendSum(document.getSendSum());
            dto.setStatisticsDate(document.getStatisticsDate());
            dto.setTopic(document.getTopic());
            dto.setUpdateTime(document.getUpdateTime());
        }
        return dto;
    }
}
