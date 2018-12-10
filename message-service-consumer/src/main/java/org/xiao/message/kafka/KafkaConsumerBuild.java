package org.xiao.message.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

public class KafkaConsumerBuild
{

    private boolean enableAutoCommit = true;

    private String sessionTimeout = "6000";

    private String autoCommitInterval = "1000";

    //private String autoOffsetReset = "latest";

    private String autoOffsetReset = "latest";

    public ConsumerFactory<String, String> consumerFactory(String groupId, String servers)
    {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(groupId, servers));
    }

    private Map<String, Object> consumerConfigs(String groupId, String servers)
    {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return propsMap;
    }

    public ContainerProperties createContainerProperties(String topic, MessageListener<String, String> messageListener)
    {
        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener(messageListener);
        return containerProperties;
    }

    public KafkaMessageListenerContainer<String, String> createKafkaMessageListenerContainer(
            ContainerProperties containerProperties, ConsumerFactory<String, String> consumerFactory)
    {
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }
}
