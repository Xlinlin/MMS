package org.xiao.message.service.impl;

import org.xiao.message.document.ServerConfigTopicRelationshipDocument;
import org.xiao.message.repository.ServerConfigTopicRelationshipRepository;
import org.xiao.message.service.ServerConfigTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * [简要描述]: 服务和主题绑定关系服务
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/5 09:56
 * @since JDK 1.8
 */
@Service
public class ServerConfigTopicServiceImpl implements ServerConfigTopicService
{
    @Autowired
    private ServerConfigTopicRelationshipRepository relationshipRepository;

    @Override
    public int updateServerConfigTopic(ServerConfigTopicRelationshipDocument document, boolean isError)
    {
        return relationshipRepository.updateServerConfigTopic(document, isError);
    }

    /**
     * [简要描述]:主题查找可用对应的服务器IP信息<br/>
     * [详细描述]:查找正常可用的服务器信息<br/>
     *
     * @param topic : 主题
     * @return java.lang.String
     * llxiao  2018/11/5 - 9:37
     **/
    @Override
    public ServerConfigTopicRelationshipDocument getServerIpByTopic(String topic)
    {
        return relationshipRepository.getServerIpByTopic(topic);
    }

    @Override
    public List<String> findTopicByServerIp(String serverIp)
    {
        return relationshipRepository.findTopicByServerIp(serverIp);
    }

    @Override
    public int bindServerConfigTopic(String topic, String serverIp)
    {
        return relationshipRepository.bindServerConfigTopic(topic, serverIp);
    }

    @Override
    public int unBindByTopic(String topic)
    {
        return relationshipRepository.unBindByTopic(topic);
    }

    @Override
    public int unBindByServerIp(String serverIp)
    {
        return unBindByServerIp(serverIp);
    }
}
