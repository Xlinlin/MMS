package org.xiao.message.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="consumer_server_log_doument")
public class ConsumerServerLogDocument {
	@Id
	private String id;
	
	private String topic;
	
	private String createTime;
	
}
