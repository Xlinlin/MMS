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
public class ServerConfigGroupDto
{
    //主键
    private String id;

    //组名称
    private String groupName;

    //描述
    private String desc;

    //创建时间
    private String createTime;

    //更新时间
    private String updateTime;

    //当前使用数量
    private int currentUseCount;



}
