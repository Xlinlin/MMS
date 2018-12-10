package org.xiao.message.api;

import lombok.Data;

@Data
public class DeliveryMessageDto {
	private String traceLogId;
	private String messageBody;
	private String messageId;
}
