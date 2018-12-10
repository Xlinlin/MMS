package org.xiao.message.monitor.controller;

import org.xiao.message.common.page.MongoPageImp;
import org.xiao.message.monitor.api.topic.api.TopicRestService;
import org.xiao.message.monitor.api.topic.dto.TopicDto;
import org.xiao.message.monitor.api.topic.query.TopicQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/topic")
public class TopicController
{
    @Autowired
    private TopicRestService topicRestService;
    /**
     * [简要描述]:获取分页主题列表
     * [详细描述]:<br/>
      * @param topicQuery :
     * @return <TopicDto>
     * xmli  2018/11/9 - 17:19
     **/
    @RequestMapping(value = "/pageTopic")
    public MongoPageImp<TopicDto> pageTopic(@RequestBody TopicQuery topicQuery)
    {
        return topicRestService.pageTopic(topicQuery);
    }
    /**
     * [简要描述]:创建主题
     * [详细描述]:<br/>
      * @param topicDto :
     * @return java.lang.String
     * xmli  2018/11/9 - 17:19
     **/
    @RequestMapping(value = "/save")
    public String saveTopic(@RequestBody TopicDto topicDto)
    {
        return topicRestService.saveTopic(topicDto);
    }
    /**
     * [简要描述]:根据topicId删除主题
     * [详细描述]:<br/>
      * @param topicId :
     * @return java.lang.String
     * xmli  2018/11/9 - 17:18
     **/
    @RequestMapping(value = "/{id}")
    public String deleteTopic(@PathVariable("id") String topicId)
    {
        return topicRestService.deleteTopic(topicId);
    }
    /**
     * [简要描述]:主题启用、禁用
     * [详细描述]:<br/>
      * @param topicId : 
     * @param status :
     * @return java.lang.String 
     * xmli  2018/11/9 - 17:11
     **/
    @RequestMapping(value = "/enable")
    public String enable(@RequestParam("topicId") String topicId, @RequestParam("status") Integer status)
    {
        return  topicRestService.enable(topicId, status);
    }
}
