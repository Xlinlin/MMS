package org.xiao.message.consumer;

import cn.hutool.core.collection.CollectionUtil;
import org.xiao.message.constant.Constants;
import org.xiao.message.document.ServerConfigDocument;
import org.xiao.message.document.TopicDocument;
import org.xiao.message.redis.MessageRedisTemplate;
import org.xiao.message.redis.RedisLock;
import org.xiao.message.service.ServerConfigService;
import org.xiao.message.service.TopicServerRelationService;
import org.xiao.message.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.List;

@Slf4j
public class NoticeMessageListener implements MessageListener
{

    @Autowired
    private MessageRedisTemplate redisTemplate;

    @Autowired
    private ServerConfigService serverConfigService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicServerRelationService topicServerRelationService;

    @Override
    public void onMessage(Message message, byte[] pattern)
    {
        byte[] body = message.getBody();
        String messageBody = (String) redisTemplate.getValueSerializer().deserialize(body);
        if (messageBody == null)
        {
            return;
        }
        String topic = "";
        boolean start = false;
        boolean stop = false;
        if (messageBody.startsWith(Constants.TOPIC_NOTICE_START))
        {
            topic = messageBody.replace(Constants.TOPIC_NOTICE_START, "");
            start = true;
        }
        else if (messageBody.startsWith(Constants.TOPIC_NOTICE_STOP))
        {
            topic = messageBody.replace(Constants.TOPIC_NOTICE_STOP, "");
            stop = true;
        }

        if (StringUtils.isNotBlank(topic))
        {
            TopicDocument document = topicService.findByTopic(topic);
            if (document == null)
            {
                log.error("主题{}的基本信息不存在!!!", topic);
                return;
            }

            if (log.isDebugEnabled())
            {
                log.debug("接收到主题处理，主题基本信息{}", document);
            }

            if (start)
            {
                startTopic(document);
            }
            else if (stop)
            {
                stopTopic(document);
            }
        }
        else
        {
            log.error("主题处理失败，主题不能为空!!!");
        }

    }

    // 停止主题
    private void stopTopic(TopicDocument document)
    {
        String topic = document.getTopic();
        if (log.isDebugEnabled())
        {
            log.info("接收到停止topic请求==" + topic);
        }
        topicServerRelationService.unBindTopicServer(topic);
        topicService.updateTopicStatus(topic, TopicDocument.STOP, document.getServerGroupId());
    }

    // 创建主题
    private void startTopic(TopicDocument document)
    {
        String topic = document.getTopic();
        if (log.isDebugEnabled())
        {
            log.debug("接收到创建topic请求==" + topic);
        }

        if (TopicDocument.START == document.getStatus())
        {
            log.error("主题{}已启动，不能重复启用该主题......");
            return;
        }

        // 使用方法，创建RedisLock对象
        RedisLock lock = new RedisLock(redisTemplate, "lock_" + topic);
        try
        {
            if (lock.lock())
            {
                List<ServerConfigDocument> configDocuments = serverConfigService.distributionServerConfig();
                if (CollectionUtil.isEmpty(configDocuments))
                {
                    log.error("不能创建主题，Broker服务器信息不存在!");
                }
                else
                {
                    StringBuffer buffer = new StringBuffer();
                    String serverGroupId = "";
                    for (ServerConfigDocument configDocument : configDocuments)
                    {
                        if (buffer.length() > 0)
                        {
                            buffer.append(",");
                        }
                        if (StringUtils.isBlank(serverGroupId))
                        {
                            serverGroupId = configDocument.getServerGroupId();
                        }
                        buffer.append(configDocument.getServerIp()).append(":").append(configDocument.getPort());
                    }
                    if (buffer.length() != 0)
                    {
                        this.topicServerRelationService.bindTopicAndServer(topic, buffer.toString());
                        topicService.updateTopicStatus(topic, TopicDocument.START, serverGroupId);
                    }
                    else
                    {
                        log.error("不能创建主题，服务组对应的服务器信息错误!");
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error("创建主题失败==" + topic, e);
        }
        finally
        {
            lock.unlock();
        }
    }

}
