package org.xiao.message.api;

import org.xiao.message.constant.Constants;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * [简要描述]: 服务IP
 * [详细描述]: 通过主题找IP地址
 *
 * @author llxiao
 * @version 1.0, 2018/11/5 15:58
 * @since JDK 1.8
 */
@FeignClient(value = Constants.MESSAGE_SERVICE_NAME)
@RequestMapping("/api/server")
public interface ServerConfigFeign
{

    @RequestMapping("/getByTopic")
    String getByTopic(@RequestParam("topic") String topic);
}
