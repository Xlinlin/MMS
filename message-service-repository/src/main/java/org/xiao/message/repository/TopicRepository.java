package org.xiao.message.repository;

import java.util.List;

import org.xiao.message.document.TopicDocument;

public interface TopicRepository extends BaseMongoRepository<TopicDocument, String>
{

    int updateTopicStatus(String topic, int status, String serverGroupId);

    int updateTopicStatus(List<String> topics, int status, String serverGroupId);

    List<TopicDocument> findStopTopics();

    TopicDocument findByTopic(String topic);

    void increase(String topic);
}
