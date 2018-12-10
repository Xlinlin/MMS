package org.xiao.message.monitor.controller;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.broker.api.BrokerConfigService;
import org.xiao.message.monitor.api.broker.api.ServerConfigService;
import org.xiao.message.monitor.api.broker.bean.query.ServerConfigQuery;
import org.xiao.message.monitor.api.groupId.bean.dto.ServerConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * [简要描述]:broker服务
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/10/31
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/monitor/broker/broker_mgt")
public class BrokerConfigController {
    @Autowired
    private BrokerConfigService brokerConfigService;
    @Autowired
    private ServerConfigService serverConfigService;


    /**
     * [简要描述]:根据serverIp查询
     * [详细描述]:
     *
     * @author nietao
     * @version 1.0,  2018/10/31
     * @since JDK 1.8
     */
    @RequestMapping("/findById")
    public ServerConfigDto findByMessageId(String serverIp) {
        return brokerConfigService.findByMessageId(serverIp);
    }

    @RequestMapping("/findPage")
    public MongoPageImp<ServerConfigDto> findPage(@RequestBody ServerConfigQuery query) {
        return brokerConfigService.findPage(query);
    }

    @PostMapping("/delete/{serverIp}")
    public int remove(@PathVariable("serverIp") String serverIp) {
        serverIp=serverIp+".";
        return brokerConfigService.remove(serverIp);
    }

    @PostMapping(path = "/save")
    public int createServerConfig(@RequestBody ServerConfigDto configDto) {
        return serverConfigService.createServerConfig(configDto);
    }

    @PostMapping(path = "/modify/{id}")
    public int updateServerConfig(@PathVariable("id") String id, @RequestBody ServerConfigDto dto){
        return serverConfigService.updateServerConfig(id+".",dto);
    }

}
