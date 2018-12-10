package org.xiao.message.bean.dto;

import lombok.Data;

/**
 * [简要描述]:短信
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/29 16:32
 * @since JDK 1.8
 */
@Data
public class SmsDto
{
    private String id;
    //电话
    private String phoneNumbers;
    //拓展字段
    private String outId;
}
