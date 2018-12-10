package org.xiao.message.bean.dto;

import lombok.Data;

@Data
public class FeedbackMessageDto {
	
	private String messageId;

	private String topic;
	
	private String traceLogId;
	
	private String consumerId;
	
	private String consumerIp;
	
	private String consumerTime;
}
