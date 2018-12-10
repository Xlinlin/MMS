package org.xiao.message.controller;

import org.xiao.message.bean.dto.ServerConfigDto;
import org.xiao.message.bean.dto.ServerConfigGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.xiao.message.service.ServerConfigService;

@RestController
@RequestMapping("/server/config")
public class ServerConfigController
{
    @Autowired
    private ServerConfigService serverConfigService;

    @PostMapping(path = "/save")
    public int createServerConfig(@RequestBody ServerConfigDto configDto)
    {
        return serverConfigService.save(configDto);
    }

    @PostMapping(path = "/remove/{serverIp}")
    public int remove(@PathVariable String serverIp)
    {

        return serverConfigService.remove(serverIp);
    }

    @PostMapping(path = "/create/group")
    public int createServerConfigGroup(@RequestBody ServerConfigGroupDto dto)
    {
        return serverConfigService.createServerConfigGroup(dto);
    }

    /**
     * [简要描述]:修改服务组
     * [详细描述]:<br/>
     *
     * @param id :
     * @param dto :
     * @return int
     * wcpeng  2018/10/31 - 16:20
     **/
    @PostMapping(path = "/modify/group/{id}")
    public int createServerConfigGroup(@PathVariable String id, @RequestBody ServerConfigGroupDto dto)
    {
        return serverConfigService.updateServerConfigGroup(id, dto);
    }

    /**
     * [简要描述]:修改服务
     * [详细描述]:<br/>
     *
     * @param id :
     * @param dto :
     * @return int
     * wcpeng  2018/10/31 - 16:20
     **/
    @PostMapping(path = "/modify/{id}")
    public int updateServerConfig(@PathVariable String id, @RequestBody ServerConfigDto dto)
    {
        return serverConfigService.updateServerConfig(id, dto);
    }
}
