package org.xiao.message.monitor.api.customer.query;

import org.xiao.message.monitor.api.groupId.bean.query.BasisQuery;
import lombok.Data;

@Data
public class ConsumerQuery extends BasisQuery
{
    private String topic;
}
