package org.xiao.message.schedule.api.taskmanager.bean.vo;/**
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
public class TaskConfigVo {
    private String id;


    /** 任务说明 **/
    private String description;


    /** 任务名称 **/
    private String name;

    /** 任务执行URL **/
    private String url;

    /** 模块 ModelEnum定义 **/
    private String module;

    /**
     * 运行状态: 0 停止 1运行 2删除
     */
    private Integer status;

    /** 创建人 **/
    private String creator;

    /** 创建时间 **/
    private String createTime;

    /** 更新时间 **/
    private String updateTime;

    /** 最后更新人 **/
    private String updater;

    /** 运行频次 **/
    private String cronExp;

    /** 是否自动启动：0否 1是 **/

    private Integer autoStart;

    /** 最新启动时间**/

    private String latestStartTime;

    /** 最后运行时间**/
    private String lastRunTime;
}
