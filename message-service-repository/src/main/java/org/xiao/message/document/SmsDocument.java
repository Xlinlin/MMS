package org.xiao.message.document;/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/30 14:00
 * @since JDK 1.8
 */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/30 14:00
 * @since JDK 1.8
 */
@Data
@Document(collection = "sms_document")
public class SmsDocument
{
    @Id
    private String id;
    //电话
    private String phoneNum;
    //短信内容
    private String content;
    //发送时间
    private String sendDate;
    //拓展字段
    private String outId;
    //发送状态 1：等待回执，2：发送失败，3：发送成功
    private Long sendStatus;

    private String bizId;
}
