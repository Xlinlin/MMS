package org.xiao.message.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiao.message.document.ServerConfigTopicRelationshipDocument;
import org.xiao.message.service.ServerConfigTopicService;

/**
 * [简要描述]: 主题获取服务器信息
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/5 15:44
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api/server")
@Slf4j
public class ServerConfigApi
{
    @Autowired
    private ServerConfigTopicService serverConfigTopicService;

    @RequestMapping("/getByTopic")
    public String getByTopic(String topic)
    {
        if (log.isDebugEnabled())
        {
            log.debug("~~~~~~~~接收到获取消息队列服务器的请求，主题：{}", topic);
        }
        ServerConfigTopicRelationshipDocument document = serverConfigTopicService.getServerIpByTopic(topic);
        if (null != document)
        {
            return document.getServerIp();
        }
        return "";
    }
}
