package org.xiao.message.manager.broker.api;

import org.xiao.message.bean.query.ServerConfigQuery;
import org.xiao.message.document.ServerConfigDocument;
import org.xiao.message.manager.broker.dto.ServerConfigDto;
import org.xiao.message.service.ServerConfigService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]:服务组管理
 * [详细描述]:
 *
 * @author wcpeng
 * @version 1.0, 2018/10/26 09:10
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/serverConfig/manager")
public class ServerConfigAdminApi
{

    @Autowired
    private ServerConfigService serverConfigService;

    /**
     * [简要描述]:根据主键获取对象
     * [详细描述]:<br/>
     *
     * @param serverIp : 主键
     * @return ServerConfigGroupDto
     * wcpeng  2018/10/26 - 13:48
     **/
    @RequestMapping("/findById")
    public ServerConfigDto findByMessageId(String serverIp)
    {
        ServerConfigDocument serverConfigGroupDocument = serverConfigService.findById(serverIp);
        return ServerConfigDto.convertDto(serverConfigGroupDocument);
    }

    /**
     * [简要描述]:分页查询
     * [详细描述]:<br/>
     *
     * @param query :
     * @return org.springframework.data.domain.Page<ServerConfigDto>
     * wcpeng  2018/10/26 - 15:45
     **/
    @RequestMapping("/findPage")
    public Page<ServerConfigDto> findPage(@RequestBody ServerConfigQuery query)
    {
        Page page = serverConfigService.findPage(query);
        List<ServerConfigDocument> list = page.getContent();
        List<ServerConfigDto> sourceDtos = new ArrayList<>();
        for (ServerConfigDocument serverConfigDocument : list)
        {
            sourceDtos.add(ServerConfigDto.convertDto(serverConfigDocument));
        }
        return new PageImpl<>(sourceDtos, new PageRequest(query.getPageNum() - 1, query.getPageSize()), page
                .getTotalElements());
    }

    /**
     * [简要描述]:根据ip地址删除
     * [详细描述]:<br/>
     *
     * @param serverIp :主键
     * @return int
     * wcpeng  2018/10/26 - 17:03
     **/
    @PostMapping("/delete/{serverIp}")
    public int remove(@PathVariable String serverIp)
    {
        return serverConfigService.remove(serverIp);
    }

    /**
     * [简要描述]:通过服务组的ID获取服务列表
     * [详细描述]:<br/>
     *
     * @return java.util.List<ServerConfigDto>
     * wcpeng  2018/10/31 - 10:36
     **/
    @RequestMapping("/getServerConfigs")
    public List<ServerConfigDto> findServerConfigByGroupId(String groupId)
    {
        List<ServerConfigDocument> serverConfigDocuments = serverConfigService.findServerConfigByGroupId(groupId);
        List<ServerConfigDto> serverConfigDtos = null;
        if (CollectionUtils.isNotEmpty(serverConfigDocuments))
        {
            serverConfigDtos = new ArrayList<>();
            for (ServerConfigDocument serverConfigDocument : serverConfigDocuments)
            {
                serverConfigDtos.add(ServerConfigDto.convertDto(serverConfigDocument));
            }
        }
        return serverConfigDtos;
    }
}
