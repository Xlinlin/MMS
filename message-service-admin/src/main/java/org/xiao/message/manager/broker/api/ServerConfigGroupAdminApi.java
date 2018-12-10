package org.xiao.message.manager.broker.api;

import org.xiao.message.bean.query.ServerConfigGroupQuery;
import org.xiao.message.document.ServerConfigGroupDocument;
import org.xiao.message.service.ServerConfigGroupService;
import org.xiao.message.manager.broker.dto.ServerConfigGroupDto;
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
@RequestMapping("/serverConfigGroup/manager")
public class ServerConfigGroupAdminApi
{

    @Autowired
    private ServerConfigGroupService serverConfigGroupService;

    /**
     * [简要描述]:根据主键获取对象
     * [详细描述]:<br/>
     *
     * @param id :
     * @return ServerConfigGroupDto
     * wcpeng  2018/10/26 - 13:48
     **/
    @RequestMapping("/findById")
    public ServerConfigGroupDto findByMessageId(String id)
    {
        ServerConfigGroupDocument serverConfigGroupDocument = serverConfigGroupService.findById(id);
        return ServerConfigGroupDto.convertDto(serverConfigGroupDocument);
    }

    /**
     * [简要描述]:分页查询
     * [详细描述]:<br/>
     *
     * @param query :
     * @return org.springframework.data.domain.Page<ServerConfigGroupDto>
     * wcpeng  2018/10/26 - 13:52
     **/
    @RequestMapping("/findPage")
    public Page<ServerConfigGroupDto> findPage(@RequestBody ServerConfigGroupQuery query)
    {
        Page page = serverConfigGroupService.findPage(query);
        List<ServerConfigGroupDocument> list = page.getContent();
        List<ServerConfigGroupDto> sourceDtos = new ArrayList<>();
        for (ServerConfigGroupDocument serverConfigGroupDocument : list)
        {
            sourceDtos.add(ServerConfigGroupDto.convertDto(serverConfigGroupDocument));
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
    public Boolean delById(@PathVariable String serverIp)
    {
        return serverConfigGroupService.delById(serverIp);
    }
}
