package org.xiao.message.monitor.api.message.bean.query;

import org.xiao.message.monitor.api.groupId.bean.query.BasisQuery;
import lombok.Data;

/**
 * [简要描述]: 消息查询
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/10/25 14:59
 * @since JDK 1.8
 */
@Data
public class MessageQuery extends BasisQuery
{
    private String messageId;
    private String topic;
    private String key;
    private String businessKey;
    private String groupId;

    //默认时间升降序 true升序fals降序
    private boolean direction;

    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;

    //消息状态 0已接收，1已发送，2已消费，3消息异常，4死信
    private Integer status;
}
