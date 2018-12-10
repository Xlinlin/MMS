package org.xiao.message.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "topic_document")
public class TopicDocument
{

    //未启用
    public static int UN_START = 0;

    //启用
    public static int START = 1;

    //禁用
    public static int STOP = 2;

    //重试次数
    public static final int RETRY_COUNT = 5;

    @Id
    private String id;

    private String topic;

    private String desc;

    private String stopTime;//开始时间

    private Integer status = 0;//状态,0禁用，1启用

    private String createTime;//创建时间

    private String startTime;//开始时间

    private String createIp;//开始创建的IP

    private String serverGroupId;//所属服务组ID

    private String topicType;

    //重发次数，到达一定次数5次时，标记禁用
    private Integer sendCount = 0;
}
