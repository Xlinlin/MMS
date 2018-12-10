package org.xiao.message.monitor.api.topic.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.topic.dto.TopicDto;
import org.xiao.message.monitor.api.topic.query.TopicQuery;
import org.xiao.message.monitor.constants.ServiceProduceConstants;

@FeignClient(value = ServiceProduceConstants.MESSAGE_ADMIN)
@RequestMapping("/topic")
public interface TopicRestService
{
    @RequestMapping(value = "/pageTopic")
    MongoPageImp<TopicDto> pageTopic(@RequestBody TopicQuery topicQuery);

    @RequestMapping(value = "/save", produces = "text/plain")
    String saveTopic(@RequestBody TopicDto topicDto);

    @RequestMapping(value = "/{id}", produces = "text/plain")
    String deleteTopic(@PathVariable("id") String topicId);

    /**
     * [简要描述]:启用/停用主题<br/>
     * [详细描述]:<br/>
     *
     * @param topicId : 主题ID
     * @param status : 状态,1启用，0禁用
     * @return String 成功返回""，否则返回异常信息
     * llxiao  2018/11/8 - 14:04
     **/
    @RequestMapping(value = "/enable", produces = "text/plain")
    String enable(@RequestParam("topicId") String topicId, @RequestParam("status") Integer status);
}
