package org.xiao.message.monitor.api.message.api;

import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/5
 * @since JDK 1.8
 */
@FeignClient(value = ServiceProduceConstants.MESSAGE_SERVICE)
@RequestMapping("/api/message")
public interface ApiMessage
{

    /**
     * [简要描述]:消息重复发送<br/>
     * [详细描述]:<br/>
     *
     * @param messageId :
     * @return java.lang.String
     * llxiao  2018/11/7 - 10:37
     **/
    @RequestMapping("/resend")
    String resendMessage(@RequestParam("messageId") String messageId);
}
