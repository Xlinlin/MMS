package org.xiao.message.controller;

import org.xiao.message.bean.dto.ConsumerDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestConsumerController extends BaseTest{
	@Autowired
	ConsumerController consumerController;
	@Test
	public void create() {
		ConsumerDto dto=new ConsumerDto();
		dto.setDesc("会员消费");
		dto.setTopic("order-test");
		dto.setConsumerCode("member-consumer");
		consumerController.create(dto);
	}
}
