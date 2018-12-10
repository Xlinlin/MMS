package org.xiao.message.schedule.api.operationlog.bean.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DispatchLogVo
{
    private String id;

    private String taskId;

    /**
     * 任务名称
     */
    private String name;
    /**
     * 调度URL
     */
    private String url;
    /**
     * 执行结果
     */
    private String result;
    /**
     * 失败原因
     */
    private String failureMsg;
    /**
     * 调度开始时间
     */
    private Date dispatchStartTime;
    /**
     * 调度结束时间
     */
    private Date dispatchEndTime;
    /**
     * 执行时长(毫秒)
     */
    private String executeTime;
}
