package org.xiao.message.service;

import org.xiao.message.document.ServerConfigTopicRelationshipDocument;

import java.util.List;

/**
 * [简要描述]:
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/5 09:54
 * @since JDK 1.8
 */
public interface ServerConfigTopicService
{

    /**
     * [简要描述]:更新状态<br/>
     * [详细描述]:<br/>
     *
     * @param document : 主题和服务器映射关系数据
     * @param isError : 是否异常，统计异常次数
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

    List<String> findTopicByServerIp(String serverIp);

    int bindServerConfigTopic(String topic, String serverIp);

    int unBindByTopic(String topic);

    int unBindByServerIp(String serverIp);
}
