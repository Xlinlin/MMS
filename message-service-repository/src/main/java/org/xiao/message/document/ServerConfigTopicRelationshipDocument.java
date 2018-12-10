package org.xiao.message.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "server_config_topic_relationship_document")
public class ServerConfigTopicRelationshipDocument
{

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    //最大错误次数
    public static final int MAX_ERROR_COUNT = 4;

    @Id
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
