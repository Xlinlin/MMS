package org.xiao.message.monitor.api.topic.dto;

import lombok.Data;
@Data
public class TopicDto {
    public static int STOP = 0;

    public static int START = 1;

    private String id;

    private String topic;

    private String desc;

    private String stopTime;//结束时间

    private Integer status = 0;//状态0-未使用,1-使用中,2-已停用

    private String createTime;//创建时间

    private String startTime;//开始时间

    private String startupIp;//开始IP

    private String topicType;

}
