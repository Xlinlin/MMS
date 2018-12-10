package org.xiao.message.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 08:58
 * @since JDK 1.8
 */
@Data
@Document(collection = "message_status_statistics_document")
public class MessageStatusStatisticsDocument
{
    @Id
    private String id;

    private Integer statusSum;

    private Integer status;

    private String createTime;

    private String statisticsDate;

    private String updateTIme;
}
