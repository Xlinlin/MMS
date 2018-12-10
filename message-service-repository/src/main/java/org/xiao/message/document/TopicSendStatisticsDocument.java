package org.xiao.message.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/14 14:18
 * @since JDK 1.8
 */
@Data
@Document(collection = "topic_send_statistics_document")
public class TopicSendStatisticsDocument
{
    @Id
    private String id;

    private String topic;

    private String statisticsDate;

    private Integer sendSum;

    private String createTime;

    private String updateTime;
}
