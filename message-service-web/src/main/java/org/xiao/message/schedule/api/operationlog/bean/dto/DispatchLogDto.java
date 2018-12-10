package org.xiao.message.schedule.api.operationlog.bean.dto;

import org.xiao.message.monitor.api.groupId.bean.query.BasisQuery;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DispatchLogDto extends BasisQuery
{
    private String taskId;
}
