package org.xiao.message.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/13 13:51
 * @since JDK 1.8
 */
@Data
@Document(collection = "message_week_send_document")
public class MessageWeekSendDocument
{
    @Id
    private String id;

    private Integer sendSum;

    private Integer successSum;

    private Integer failSum;

    private Integer deadSum;

    private String createTime;

    private String statisticsDate;

    private String updateTime;
}
