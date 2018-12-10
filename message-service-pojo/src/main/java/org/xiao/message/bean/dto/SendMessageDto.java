package org.xiao.message.bean.dto;

import lombok.Data;

@Data
public class SendMessageDto {
	private String topic;
	private String message;
	private String key;
	private String businessKey;

    private String messageId;
    private Integer status;
	
}
