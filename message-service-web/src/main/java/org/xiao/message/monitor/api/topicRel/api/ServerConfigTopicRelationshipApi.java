package org.xiao.message.monitor.api.topicRel.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.topicRel.dto.ServerConfigTopicRelationshipDto;
import org.xiao.message.monitor.api.topicRel.query.RelateshipQuery;
import org.xiao.message.monitor.constants.ServiceProduceConstants;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author nietao
 * @version 1.0,  2018/11/5
 * @since JDK 1.8
 */
@FeignClient(value = ServiceProduceConstants.MESSAGE_ADMIN)
@RequestMapping("/serverConfig/relationship")
public interface ServerConfigTopicRelationshipApi
{
    @RequestMapping(value = "/findPage")
    MongoPageImp<ServerConfigTopicRelationshipDto> findPage(@RequestBody RelateshipQuery relateshipQuery);
}
