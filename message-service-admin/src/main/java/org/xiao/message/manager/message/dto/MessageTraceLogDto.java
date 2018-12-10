package org.xiao.message.manager.message.dto;

import org.xiao.message.document.MessageTraceLogDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

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

    @Indexed
    private String messageId;
    private String topic;

    private String errorMsg;

    //消息链上的位置
    // 消息链路：客户端发送->服务端接收->已发出到redis->已从Redis读取->已发送到kafka->已消费
    private Integer position;

    public static MessageTraceLogDto convertDto(MessageTraceLogDocument document)
    {
        MessageTraceLogDto messageTraceLogDto = new MessageTraceLogDto();
        messageTraceLogDto.setTraceLogId(document.getTraceLogId());
        messageTraceLogDto.setReceiveIp(document.getReceiveIp());
        messageTraceLogDto.setReceiveTime(document.getReceiveTime());
        messageTraceLogDto.setSendTime(document.getSendTime());
        messageTraceLogDto.setSendIp(document.getSendIp());
        messageTraceLogDto.setConsumerIp(document.getConsumerIp());
        messageTraceLogDto.setConsumerId(document.getConsumerId());
        messageTraceLogDto.setConsumerTime(document.getConsumerTime());
        messageTraceLogDto.setConsumerCode(document.getConsumerCode());
        messageTraceLogDto.setGroupId(document.getGroupId());
        messageTraceLogDto.setMessageId(document.getMessageId());
        messageTraceLogDto.setTopic(document.getTopic());
        messageTraceLogDto.setErrorMsg(document.getErrorMsg());
        messageTraceLogDto.setPosition(document.getPosition());
        return messageTraceLogDto;
    }
}
