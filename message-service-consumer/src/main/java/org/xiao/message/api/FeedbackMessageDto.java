package org.xiao.message.api;

import lombok.Data;

@Data
public class FeedbackMessageDto {
	
	private String traceLogId;
	private String messageId;
	private String consumerId;

    private String topic;
	
	private String consumerIp;
	
	private String consumerTime;
}
