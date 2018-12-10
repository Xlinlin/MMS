package org.xiao.message.monitor.api.message.bean.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class MessageTraceLogDto
{

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


    private String messageId;
    private String topic;

    private String errorMsg;

    //消息链上的位置
    // 消息链路：客户端发送->服务端接收->已发出到redis->已从Redis读取->已发送到kafka->已消费
    private Integer position;


}
