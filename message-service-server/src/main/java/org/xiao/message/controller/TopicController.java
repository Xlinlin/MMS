package org.xiao.message.controller;

import org.xiao.message.bean.dto.TopicDto;
import org.xiao.message.constant.Constants;
import org.xiao.message.document.TopicDocument;
import org.xiao.message.redis.MessageRedisTemplate;
import org.xiao.message.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
@Slf4j
public class TopicController
{
    @Autowired
    private TopicService topicService;

    @Autowired
    private MessageRedisTemplate redisTemplate;

    @PostMapping(path = "/create")
    public int createTopic(@RequestBody TopicDto topicDto)
    {
        if (StringUtils.isBlank(topicDto.getTopic()))
        {
            log.error("主题不能为空!!!");
            return 1;
        }
        TopicDocument document = new TopicDocument();
        document.setDesc(topicDto.getDesc());
        document.setTopic(topicDto.getTopic());
        int result = topicService.saveTopic(document);
        redisTemplate
                .convertAndSend(Constants.TOPIC_NOTICE_PRODUCER, Constants.TOPIC_NOTICE_START + document.getTopic());
        return result;
    }

    @GetMapping(path = "/remove/{id}")
    public int remove(@PathVariable String id)
    {
        if (StringUtils.isNotBlank(id))
        {
            TopicDocument document = topicService.removeTopic(id);
            if (document != null)
            {
                redisTemplate.convertAndSend(Constants.TOPIC_NOTICE_PRODUCER,
                        Constants.TOPIC_NOTICE_STOP + document.getTopic());
            }
        }
        return 1;
    }

}
