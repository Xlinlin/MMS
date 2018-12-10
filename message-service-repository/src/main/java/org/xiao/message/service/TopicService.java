package org.xiao.message.service;

import org.xiao.message.bean.query.TopicQuery;
import org.xiao.message.document.TopicDocument;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService
{

    List<TopicDocument> findStopTopics();

    int saveTopic(TopicDocument document);

    void updateTopicStatus(String topic, int status, String serverGroupId);

    void updateTopicStatus(List<String> topics, int status, String serverGroupId);

    TopicDocument findByTopic(String topic);

    TopicDocument removeTopic(String id);

    /**
     * [简要描述]:分页获取<br/>
     * [详细描述]:<br/>
     *
     * @param topicQuery :
     * @return org.springframework.data.domain.Page
     * jun.liu  2018/10/26 - 9:07
     **/
    Page findPage(TopicQuery topicQuery);

    TopicDocument getById(String topicId);

    /**
     * [简要描述]:主题发起创建请求自增<br/>
     * [详细描述]:<br/>
     *
     * @param topic : 自增
     * llxiao  2018/11/8 - 19:58
     **/
    void increase(String topic);
}
