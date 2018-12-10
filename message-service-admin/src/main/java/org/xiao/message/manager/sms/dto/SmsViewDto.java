package org.xiao.message.manager.sms.dto;

import org.xiao.message.document.SmsDocument;
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

    public static SmsViewDto convertToSmsViewDto(SmsDocument smsDocument)
    {
        SmsViewDto smsViewDto = null;
        if (smsDocument!=null)
        {
            smsViewDto = new SmsViewDto();
            smsViewDto.setContent(smsDocument.getContent());
            smsViewDto.setId(smsDocument.getId());
            smsViewDto.setOutId(smsDocument.getOutId());
            smsViewDto.setPhoneNum(smsDocument.getPhoneNum());
            smsViewDto.setSendDate(smsDocument.getSendDate());
            smsViewDto.setSendStatus(smsDocument.getSendStatus());
        }
        return smsViewDto;
    }
}
