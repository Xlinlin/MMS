package org.xiao.message.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection="consumber_fackback_document")
public class MessageFackbackDocument {
	@Id
	private String consumberFackbackId;
	/**
	 * 消息ID
	 */
	@Indexed
	private String messageId;
	/**
	 * 发送次数
	 */
	private Integer sendCount;
	
	/**
	 * 反馈时间
	 */
	private String createTime;
	private String consumerCode;
	private String groupId;
	private String topic;
    private String tranceId;
	
	/**
	 * 反馈时间
	 */
	private String feedbackTime;

    /**
	 * 消费者Ip
	 */
	private String consumerIp;
	
	
	
}
