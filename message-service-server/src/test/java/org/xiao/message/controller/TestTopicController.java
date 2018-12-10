package org.xiao.message.controller;

import org.xiao.message.MessageApplicationTest;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

public class TestTopicController extends MessageApplicationTest
{

    @Test
    public void createTopic() throws Exception
    {
        String url = "/topic/create";
        String jsonParams = "{\"topic\":\"test-message\",\"desc\":\"消息流程测试--别别别删我的！\"}";
        ResultActions resultActions = this.testBasePostApi(url, null, jsonParams);
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }

    public void remove()
    {

    }
}
