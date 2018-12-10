package org.xiao.message.consumer;

import lombok.Data;

@Data
public class KafkaDeliveryMessageDto {
	private String traceLogId;
	private String messageBody;
	private String messageId;
}
