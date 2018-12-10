package org.xiao.message.monitor.api.broker.api;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.broker.bean.query.ServerConfigQuery;
import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.xiao.message.monitor.api.groupId.bean.dto.ServerConfigDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = ServiceProduceConstants.MESSAGE_ADMIN)
@RequestMapping("/serverConfig/manager")
public interface BrokerConfigService
{
    @RequestMapping("/findById")
    ServerConfigDto findByMessageId(@RequestParam("serverIp") String serverIp);

    @RequestMapping("/findPage")
    MongoPageImp<ServerConfigDto> findPage(@RequestBody ServerConfigQuery query);

    @PostMapping("/delete/{serverIp}")
    int remove(@PathVariable("serverIp") String serverIp);

}
