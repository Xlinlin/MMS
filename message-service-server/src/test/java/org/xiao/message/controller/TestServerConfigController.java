package org.xiao.message.controller;

import org.xiao.message.bean.dto.ServerConfigDto;
import org.xiao.message.bean.dto.ServerConfigGroupDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestServerConfigController extends BaseTest {

	@Autowired
	ServerConfigController serverConfigController;

    @Test
	public void createServerConfigGroup() {
		
		ServerConfigGroupDto serverConfigGroupDto=new ServerConfigGroupDto();
        serverConfigGroupDto.setGroupName("llxiao-消息流程测试");
        serverConfigGroupDto.setDesc("llxiao-消息流程测试");
		
		serverConfigController.createServerConfigGroup(serverConfigGroupDto);
		
	}
	
	@Test
	public void createServerConfig() {
		
		ServerConfigDto serverConfigDto=new ServerConfigDto();
		serverConfigDto.setServerIp("192.168.206.208");
		serverConfigDto.setPort(9092);
        //		serverConfigDto.setServerGroupId("9cbbcf27-72df-4690-8c92-4a55b19d1b7b");
        serverConfigDto.setServerGroupId("235857ab-bd2b-4607-9f96-39c43f3fa966");
        serverConfigDto.setDesc("消息流程测试-别动别动");
        //		serverConfigDto.setDesc("业务单点");

		
		serverConfigController.createServerConfig(serverConfigDto);
		
	}

}
