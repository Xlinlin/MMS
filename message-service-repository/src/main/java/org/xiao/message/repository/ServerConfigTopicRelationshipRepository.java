package org.xiao.message.repository;

import org.xiao.message.document.ServerConfigTopicRelationshipDocument;

import java.util.List;

public interface ServerConfigTopicRelationshipRepository
        extends BaseMongoRepository<ServerConfigTopicRelationshipDocument, String>
{
    List<String> findTopicByServerIp(String serverIp);

    int bindServerConfigTopic(String topic, String serverIp);

    /**
     * [简要描述]:更新状态<br/>
     * [详细描述]:<br/>
     *
     * @param document 主题服务器映射数据
     * @param isError : true异常
     * @return int
     * llxiao  2018/11/5 - 9:24
     **/
    int updateServerConfigTopic(ServerConfigTopicRelationshipDocument document, boolean isError);

    /**
     * [简要描述]:主题查找可用对应的服务器IP信息<br/>
     * [详细描述]:查找正常可用的服务器信息<br/>
     *
     * @param topic : 主题
     * @return java.lang.String
     * llxiao  2018/11/5 - 9:37
     **/
    ServerConfigTopicRelationshipDocument getServerIpByTopic(String topic);

    int unBindByTopic(String topic);

    int unBindByServerIp(String serverIp);
}
