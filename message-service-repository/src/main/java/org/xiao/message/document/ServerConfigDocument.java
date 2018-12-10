package org.xiao.message.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection="server_config_document")
public class ServerConfigDocument {
	@Id
	private String serverIp;
	
	private String serverGroupId;
	
	private int port;
	
	private String createTime;//开始时间
	
	private String desc;
 
}
