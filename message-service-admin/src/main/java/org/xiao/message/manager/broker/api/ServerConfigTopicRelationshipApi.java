package org.xiao.message.manager.broker.api;

import org.xiao.message.bean.query.RelateshipQuery;
import org.xiao.message.document.ServerConfigTopicRelationshipDocument;
import org.xiao.message.service.ServerConfigTopicRelationshipService;
import org.xiao.message.manager.broker.dto.ServerConfigTopicRelationshipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/11/5 15:24
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "/serverConfig/relationship")
public class ServerConfigTopicRelationshipApi
{
    @Autowired
    private ServerConfigTopicRelationshipService serverConfigTopicRelationshipService;

    /**
     *[简要描述]:分页获取<br/>
     *[详细描述]:<br/>
     * @param relateshipQuery :
     * @return org.springframework.data.domain.Page<ServerConfigTopicRelationshipDto>
     * jun.liu  2018/11/5 - 16:05
     **/
    @RequestMapping(value = "/findPage")
    public Page<ServerConfigTopicRelationshipDto> findPage(@RequestBody RelateshipQuery relateshipQuery)
    {
        Page page = serverConfigTopicRelationshipService.findPage(relateshipQuery);
        List<ServerConfigTopicRelationshipDocument> list = page.getContent();
        List<ServerConfigTopicRelationshipDto> dtoList = new ArrayList<>();
        for (ServerConfigTopicRelationshipDocument document:list)
        {
            dtoList.add(ServerConfigTopicRelationshipDto.convertToDto(document));
        }
        return new PageImpl<>(dtoList, new PageRequest(relateshipQuery.getPageNum() - 1, relateshipQuery.getPageSize()), page
                .getTotalElements());
    }
}
