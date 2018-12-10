package org.xiao.message.monitor.api.message.bean.dto;

import lombok.Data;

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



}
