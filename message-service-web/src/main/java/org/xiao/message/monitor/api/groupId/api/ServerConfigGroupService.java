package org.xiao.message.monitor.api.groupId.api;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.constants.ServiceProduceConstants;
import org.xiao.message.monitor.api.groupId.bean.dto.ServerConfigGroupDto;
import org.xiao.message.monitor.api.groupId.bean.query.ServerConfigGroupQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = ServiceProduceConstants.MESSAGE_ADMIN)
@RequestMapping("/serverConfigGroup/manager")
public interface ServerConfigGroupService
{
    /**
     * [简要描述]:分页查询
     * [详细描述]:<br/>
     *
     * @param query :
     * @return
     * nietao  2018/10/30 - 11:52
     **/
    @RequestMapping("/findPage")
    MongoPageImp<ServerConfigGroupDto> findPage(@RequestBody ServerConfigGroupQuery query);

    @PostMapping("/delete/{serverIp}")
    Boolean delById(@PathVariable("serverIp") String serverIp);
}
