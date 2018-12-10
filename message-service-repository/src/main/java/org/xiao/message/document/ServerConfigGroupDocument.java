package org.xiao.message.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="server_config_group_document")
public class ServerConfigGroupDocument {
	@Id
	private String id;
	private String groupName;
	private String desc;
	private String createTime;
	private String updateTime;
	private int  currentUseCount;
}
