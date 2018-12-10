package org.xiao.message.monitor.api.topic.query;

import org.xiao.message.monitor.api.groupId.bean.query.BasisQuery;
import lombok.Data;

@Data
public class TopicQuery extends BasisQuery {
    private String topic;
    private Integer status;
    private String startTime;
    private String stopTime;
}
