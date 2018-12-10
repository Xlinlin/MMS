package org.xiao.message.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/***
 * 
 * @author zycen
 *
 */
@Data
@Document(collection="message_source_document")
public class MessageSourceDocument {

    //已接收
    public static final int RECEIVED = 0;
    // 已发送
    public static final int SEND = 1;
    // 已消费
    public static final int CONSUMED = 2;
    // 消息异常
    public static final int ABNORMAL = 3;
    // 死信
    public static final int DEAD = 4;

	// 死信发送标识
	public static final int DEAD_MESSAGE_SEND = 5;

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

	/**
	 * 接受者IP
	 */
	private String receiveIp;
	private String receiveTime;

	/**
	 * 创建IP
	 */
	private String createIp;
	private String createTime;

    //消息状态 0已接收，1已发送，2已消费，3消息异常，4死信
    private Integer status;

    //发送次数（达到一定次数不进行发送，标记为死信）
    private Integer sendCount;
}
