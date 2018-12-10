package org.xiao.message.manager.message.dto;

import org.xiao.message.document.MessageSourceDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;

/***
 *
 * @author zycen
 *
 */
@Data
public class MessageSourceDto
{

    /**
     * 消息ID
     */
    @Id
    private String messageId;
    private String topic;
    private String groupId;
    private String message;
    private String key;
    private String businessKey;
    /**
     * 生产者Ip
     */
    private String producerIp;
    private String producerTime;

    private String receiveIp;
    private String receiveTime;

    private String createIp;
    private String createTime;

    //消息状态 0已接收，1已发送，2已消费，3消息异常，4死信
    private Integer status;

    //发送次数（达到一定次数不进行发送，标记为死信）
    private Integer sendCount;

    public static MessageSourceDto convertDto(MessageSourceDocument messageSourceDocument)
    {
        MessageSourceDto messageSourceDto = new MessageSourceDto();
        messageSourceDto.setMessageId(messageSourceDocument.getMessageId());
        messageSourceDto.setTopic(messageSourceDocument.getTopic());
        messageSourceDto.setGroupId(messageSourceDocument.getGroupId());
        messageSourceDto.setMessage(messageSourceDocument.getMessage());
        messageSourceDto.setKey(messageSourceDocument.getKey());
        messageSourceDto.setBusinessKey(messageSourceDocument.getBusinessKey());
        messageSourceDto.setProducerIp(messageSourceDocument.getProducerIp());
        messageSourceDto.setProducerTime(messageSourceDocument.getProducerTime());
        messageSourceDto.setReceiveIp(messageSourceDocument.getReceiveIp());
        messageSourceDto.setReceiveTime(messageSourceDocument.getReceiveTime());
        messageSourceDto.setCreateIp(messageSourceDocument.getCreateIp());
        messageSourceDto.setCreateTime(messageSourceDocument.getCreateTime());
        messageSourceDto.setStatus(messageSourceDocument.getStatus());
        messageSourceDto.setSendCount(messageSourceDocument.getSendCount());
        return messageSourceDto;
    }

}
