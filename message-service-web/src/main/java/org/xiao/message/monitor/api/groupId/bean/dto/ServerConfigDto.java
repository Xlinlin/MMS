package org.xiao.message.monitor.api.groupId.bean.dto;

import lombok.Data;

/**
 * [简要描述]:服务组Dto
 * [详细描述]:
 *
 * @author wcpeng
 * @version 1.0, 2018/10/26 09:45
 * @since JDK 1.8
 */
@Data
public class ServerConfigDto
{
    //主键   服务ip
    private String serverIp;

    //服务组ID
    private String serverGroupId;

    //端口
    private int port;

    //创建时间
    private String createTime;

    //描述
    private String desc;


}
