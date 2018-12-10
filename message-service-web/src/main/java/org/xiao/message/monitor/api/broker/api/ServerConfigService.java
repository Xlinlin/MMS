package org.xiao.message.monitor.api.broker.api;

import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.xiao.message.monitor.api.groupId.bean.dto.ServerConfigDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = ServiceProduceConstants.MESSAGE_SERVICE)
@RequestMapping("/server/config")
public interface ServerConfigService
{
    @PostMapping(path = "/save")
    int createServerConfig(@RequestBody ServerConfigDto configDto);

    @PostMapping(path = "/remove/{serverIp}")
    int remove(@PathVariable("serverIp") String serverIp);

    @PostMapping(path = "/modify/{id}")
    int updateServerConfig(@PathVariable("id") String id, @RequestBody ServerConfigDto dto);

}
