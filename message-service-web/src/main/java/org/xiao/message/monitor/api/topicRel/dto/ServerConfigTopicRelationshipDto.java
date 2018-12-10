package org.xiao.message.monitor.api.topicRel.dto;

import lombok.Data;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/5 15:31
 * @since JDK 1.8
 */
@Data
public class ServerConfigTopicRelationshipDto
{
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    //最大错误次数
    public static final int MAX_ERROR_COUNT = 4;

    private String id;
    private String topic;
    private String serverIp;
    private String createTime;

    //更新时间
    private String updateTime;

    //错误次数
    private Integer errorCount;

    //0正常，1异常
    private Integer status;

    //异常消息
    private String exceptionMsg;

}
