package org.xiao.message.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="consumer_document")
public class ConsumerDocument {
	@Id
	private String consumerId;
	private String consumerCode;
	private String topic;
	private String desc;
	private String createTime;//开始时间
	
}
