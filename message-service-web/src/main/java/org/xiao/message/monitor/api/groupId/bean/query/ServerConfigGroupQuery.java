package org.xiao.message.monitor.api.groupId.bean.query;

import lombok.Data;

import java.util.Date;

/**
 * [简要描述]:服务组查询
 * [详细描述]:
 *
 * @author wcpeng
 * @version 1.0, 2018/10/26 09:24
 * @since JDK 1.8
 */
@Data
public class ServerConfigGroupQuery extends BasisQuery
{
    //组名称
    private String groupName;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //默认时间升降序 true升序fals降序
    private boolean direction;
}
