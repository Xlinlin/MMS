package org.xiao.message.manager.consumer;

import org.xiao.MessageAdminHttpTest;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/6 15:46
 * @since JDK 1.8
 */
public class ConsumerApiTest extends MessageAdminHttpTest
{
    private static String basisApi = "/consumer";

    @Test
    public void findPageTest() throws Exception
    {
        String url = basisApi + "/pageConsumer";
        String params = "{\"topic\":\"init_test\"}";
        ResultActions resultActions = this.testBasePostApi(url, null, params);
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }
}
