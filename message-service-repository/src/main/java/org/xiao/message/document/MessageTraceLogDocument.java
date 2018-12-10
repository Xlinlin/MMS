package org.xiao.message.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "message_trace_log_document")
public class MessageTraceLogDocument
{
    // 已接收消息
    public static final int POSITION_RECEIVE = 0;
    // 已发送到redis
    public static final int POSITION_TO_REDIS = 1;
    // 已从redis接收消息
    public static final int POSITION_FOR_REDIS = 2;
    // 已发送到kafka
    public static final int POSITION_TO_KAFKA = 3;
    // 已消费
    public static final int POSITION_CONSUMED = 4;

    @Id
    private String traceLogId;

    //接受者
    private String receiveIp;
    private String receiveTime;

    //发送者
    private String sendTime;
    private String sendIp;

    private String consumerIp;
    private String consumerId;
    private String consumerTime;
    private String consumerCode;
    private String groupId;

    @Indexed
    private String messageId;
    private String topic;

    private String errorMsg;

    //消息链上的位置
    // 消息链路：客户端发送->服务端接收->已发出到redis->已从Redis读取->已发送到kafka->已消费
    private Integer position;
}
