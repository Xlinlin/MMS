package org.xiao.message.monitor.api.groupId.api;

import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.xiao.message.monitor.api.groupId.bean.dto.ServerConfigGroupDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = ServiceProduceConstants.MESSAGE_SERVICE)
@RequestMapping("/server/config")
public interface ServerConfigService
{
    @PostMapping(path = "/create/group")
    int createServerConfigGroup(@RequestBody ServerConfigGroupDto dto);

    @PostMapping(path = "/modify/group/{id}")
    int createServerConfigGroup(@PathVariable("id") String id, @RequestBody ServerConfigGroupDto dto);

}
