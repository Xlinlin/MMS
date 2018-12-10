package org.xiao.message.schedule.api.taskmanager.bean.dto;/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/2
 * @since JDK 1.8
 */

import lombok.Data;
import lombok.ToString;

/**
 * [简要描述]:
 * [详细描述]:
 *  @author nietao
 *  @version 1.0,  2018/11/2
 *  @since JDK 1.8
 */
@Data
@ToString
public class TaskConfigDto {

    private String id;

    /** 任务说明 **/
    private String description;

    /** 任务名称 **/
    private String name;

    /** 任务执行URL **/
    private String url;

    /** 模块 **/
    private String module;

    /** 创建人 **/
    private String creator;

    /** 更新人 **/
    private String updater;

    /** 运行频次 **/
    private String cronExp;

    /** 是否自动启动：0否 1是 **/

    private Integer autoStart;
}
