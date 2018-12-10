package org.xiao.message.manager.message;

import org.xiao.MessageAdminHttpTest;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/10/31 16:48
 * @since JDK 1.8
 */
public class MessageApiTest extends MessageAdminHttpTest
{
    @Test
    public void testFindById() throws Exception
    {
        String url = "/message/manager/findByMessageId";
        Map<String, String> param = new HashMap<>();
        param.put("messageId", "be3f2f79-7533-4c50-9e78-8952d702c6f5");
        ResultActions resultActions = this.testBasePostApi(url, param, null);
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void testFindPage() throws Exception
    {
        String url = "/message/manager/findPage";
        //        String jsonParam = "{\"messageId\":\"be3f2f79-7533-4c50-9e78-8952d702c6f5\"}";
        String jsonParam = "{\"topic\":\"be3f2f79-7533-4c50-9e78-8952d702c6f5\"}";
        ResultActions resultActions = this.testBasePostApi(url, null, jsonParam);
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void test1() throws Exception
    {
        String url = "/message/statistics/listMessageWeekSend?date=" + new Date();
        ResultActions resultActions = this.testBasePostApi(url, null, null);
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }
}
