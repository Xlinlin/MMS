package org.xiao.message.monitor.api.sms.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * [简要描述]:短信
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/29 16:32
 * @since JDK 1.8
 */
@Data
public class SmsViewDto
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

}
