package org.xiao.message.monitor.api.broker.bean.query;

import org.xiao.message.monitor.api.groupId.bean.query.BasisQuery;
import lombok.Data;

import java.util.Date;

/**
 * [简要描述]:Broker资源管理查询
 * [详细描述]:
 *
 * @author wcpeng
 * @version 1.0, 2018/10/26 09:28
 * @since JDK 1.8
 */
@Data
public class ServerConfigQuery extends BasisQuery
{
    //ip地址
    private String serverIp;

    //服务组ID
    private String serverGroupId;

    //开始时间
    private Date createTime;

    //结束时间
    private Date endTime;

    //默认时间升降序 true升序fals降序
    private boolean direction;
}
