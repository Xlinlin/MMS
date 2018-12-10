package org.xiao.message.schedule.api.taskmanager.bean.dto;/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/2
 * @since JDK 1.8
 */

import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *  @author nietao
 *  @version 1.0,  2018/11/2
 *  @since JDK 1.8
 */
@Data
public class TaskSearchDto {
    /** 模块 ModelEnum定义 **/
    private String module;

    /** 任务名称 **/
    private String name;

    /** 任务说明 **/
    private String description;
    /**
     * 状态：0 停止 1运行
     */
    private Integer status;

}
