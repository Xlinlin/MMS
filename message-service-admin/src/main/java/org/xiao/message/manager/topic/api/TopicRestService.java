package org.xiao.message.manager.topic.api;

import org.xiao.message.bean.query.TopicQuery;
import org.xiao.message.constant.Constants;
import org.xiao.message.document.TopicDocument;
import org.xiao.message.manager.topic.dto.TopicDto;
import org.xiao.message.redis.MessageRedisTemplate;
import org.xiao.message.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * [简要描述]:主题管理
 * [详细描述]:
 *
 * @author jun.liu
 * @version 1.0, 2018/10/25 15:01
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "/topic")
@Slf4j
public class TopicRestService
{
    @Autowired
    private TopicService topicService;

    @Autowired
    private MessageRedisTemplate redisTemplate;

    /**
     * [简要描述]:分页查询<br/>
     * [详细描述]:<br/>
     *
     * @param topicQuery :
     * @return org.springframework.data.domain.Page<TopicDto>
     * jun.liu  2018/10/26 - 9:03
     **/
    @RequestMapping(value = "/pageTopic")
    public Page<TopicDto> pageTopic(@RequestBody TopicQuery topicQuery)
    {
        Page page = topicService.findPage(topicQuery);
        List<TopicDocument> list = page.getContent();
        List<TopicDto> dtoList = new ArrayList<>();
        for (TopicDocument topicDocument : list)
        {
            dtoList.add(TopicDto.convertToTopicDto(topicDocument));
        }
        return new PageImpl<>(dtoList, new PageRequest(topicQuery.getPageNum() - 1, topicQuery.getPageSize()), page
                .getTotalElements());
    }

    /**
     * [简要描述]:保存topic<br/>
     * [详细描述]:<br/>
     *
     * @param topicDto :
     * @return String 成功返回""，否则返回异常信息
     * jun.liu  2018/10/26 - 10:39
     **/
    @RequestMapping(value = "/save")
    public String saveTopic(@RequestBody TopicDto topicDto)
    {
        if (StringUtils.isBlank(topicDto.getTopic()))
        {
            return "保存主题失败，主题参数不能为空!";
        }
        String topic = topicDto.getTopic();
        TopicDocument topicDocument = topicService.findByTopic(topic);
        if (null != topicDocument)
        {
            return "主题已经存在，不能重复创建主题!";
        }
        int a = topicService.saveTopic(TopicDto.convertToTopicDocument(topicDto));
        if (a > 0)
        {
            //发送到redis
            redisTemplate.convertAndSend(Constants.TOPIC_NOTICE_PRODUCER,
                    Constants.TOPIC_NOTICE_START + topicDto.getTopic());
            return "";
        }
        return "主题保存失败!";
    }

    /**
     * [简要描述]:删除topic<br/>
     * [详细描述]:<br/>
     *
     * @param topicId :
     * @return String 成功返回""，否则返回异常信息
     * jun.liu  2018/10/26 - 15:49
     **/
    @RequestMapping(value = "/{id}")
    public String deleteTopic(@PathVariable("id") String topicId)
    {
        if (StringUtils.isBlank(topicId))
        {
            return "删除主题失败，主题ID不能为空!";
        }
        TopicDocument a = topicService.removeTopic(topicId);
        if (a != null)
        {
            return "";
        }
        return "删除失败，该主题不存在！";
    }

    /**
     * [简要描述]:启用/停用主题<br/>
     * [详细描述]:<br/>
     *
     * @param topicId : 主题ID
     * @param status : 状态,1启用，0禁用
     * @return String 成功返回""，否则返回异常信息
     * llxiao  2018/11/8 - 14:04
     **/
    @RequestMapping("/enable")
    public String enable(String topicId, Integer status)
    {
        if (StringUtils.isBlank(topicId))
        {
            return "主题ID不能为空!";
        }
        status = null == status ? TopicDocument.STOP : status;
        TopicDocument document = topicService.getById(topicId);
        if (null == document)
        {
            return "主题不存在!";
        }
        if (TopicDocument.START == status)
        {
            //发送到redis
            redisTemplate.convertAndSend(Constants.TOPIC_NOTICE_PRODUCER,
                    Constants.TOPIC_NOTICE_START + document.getTopic());
        }
        else
        {
            //发送到redis
            redisTemplate
                    .convertAndSend(Constants.TOPIC_NOTICE_PRODUCER, Constants.TOPIC_NOTICE_STOP + document.getTopic());
        }
        return "";
    }
}
