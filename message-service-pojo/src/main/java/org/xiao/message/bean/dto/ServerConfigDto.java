package org.xiao.message.bean.dto;

import lombok.Data;

@Data
public class ServerConfigDto {
	private String serverIp;
	private int port;
	private String serverGroupId;
	private String desc;
}
