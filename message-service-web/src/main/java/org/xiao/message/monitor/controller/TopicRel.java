package org.xiao.message.monitor.controller;/**
 * [简要描述]:
 * [详细描述]:
 *
 * @since JDK 1.8
 */

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.topicRel.api.ServerConfigTopicRelationshipApi;
import org.xiao.message.monitor.api.topicRel.dto.ServerConfigTopicRelationshipDto;
import org.xiao.message.monitor.api.topicRel.query.RelateshipQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * [简要描述]:
 * [详细描述]:
 *  @author nietao
 *  @version 1.0,  2018/11/5
 *  @since JDK 1.8
 */
@RestController
@RequestMapping("/monitor/topicRelevance/topic_rel")
public class TopicRel
{
    @Autowired
    private ServerConfigTopicRelationshipApi serverConfigTopicRelationshipApi;

    @RequestMapping(value = "/findPage")
    public MongoPageImp<ServerConfigTopicRelationshipDto> findPage(@RequestBody RelateshipQuery relateshipQuery)
    {
        return serverConfigTopicRelationshipApi.findPage(relateshipQuery);
    }
}
