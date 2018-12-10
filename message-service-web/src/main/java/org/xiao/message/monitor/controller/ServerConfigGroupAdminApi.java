package org.xiao.message.monitor.controller;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.groupId.api.ServerConfigGroupService;
import org.xiao.message.monitor.api.groupId.api.ServerConfigService;
import org.xiao.message.monitor.api.groupId.bean.dto.ServerConfigGroupDto;
import org.xiao.message.monitor.api.groupId.bean.query.ServerConfigGroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitor/groupId/groupId_mgt")
public class ServerConfigGroupAdminApi {
    @Autowired
    private ServerConfigGroupService configGroupService;
    @Autowired
    private ServerConfigService configService;
/**
 * [简要描述]:查询GroupId数据
 * [详细描述]:
 *  @author nietao
 *  @version 1.0,  2018/11/1
 *  @since JDK 1.8
*/
    @RequestMapping("/findPage")
    public MongoPageImp<ServerConfigGroupDto> findPage(@RequestBody ServerConfigGroupQuery query){
        return configGroupService.findPage(query);
    }
    /**
     * [简要描述]:保存GroupId数据
     * [详细描述]:
     *  @author nietao
     *  @version 1.0,  2018/11/1
     *  @since JDK 1.8
    */
    @PostMapping(path = "/create/group")
    public int createServerConfigGroup(@RequestBody ServerConfigGroupDto dto){
        return configService.createServerConfigGroup(dto);
    }
    /**
     * [简要描述]:修改GroupId数据
     * [详细描述]:
     *  @author nietao
     *  @version 1.0,  2018/11/1
     *  @since JDK 1.8
    */
    @PostMapping(path = "/modify/group/{id}")
    public int updateServerConfigGroup(@PathVariable("id") String id, @RequestBody ServerConfigGroupDto dto){
        return configService.createServerConfigGroup(id,dto);
    }
    @PostMapping("/delete/{serverIp}")
    public Boolean delById(@PathVariable("serverIp") String serverIp)
    {
        return configGroupService.delById(serverIp);
    }
}
