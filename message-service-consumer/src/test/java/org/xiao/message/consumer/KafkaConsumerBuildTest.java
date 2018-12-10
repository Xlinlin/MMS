package org.xiao.message.consumer;

import org.xiao.message.kafka.KafkaConsumerBuild;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

public class KafkaConsumerBuildTest
{
    public static void main(String[] args)
    {
        String servers = "192.168.206.211:9092";
        String topic = "test-message";
        String consumerId = "llxiao-test";
        KafkaConsumerBuild build = new KafkaConsumerBuild();

        ConsumerFactory<String, String> consumerFactory = build.consumerFactory(consumerId, servers);

        ContainerProperties containerProperties = build.createContainerProperties(topic, new BaseKafkaConsumerServer()
        {
            @Override
            public void consumerMessage(String messageBody)
            {
                System.out.println(messageBody);
            }

            @Override
            public String getConsumerId()
            {
                return consumerId;
            }

            @Override
            public String getTopic()
            {
                return topic;
            }

            @Override
            public MessageListener getMessageListener()
            {
                return this;
            }
        });
        KafkaMessageListenerContainer<String, String> container = build
                .createKafkaMessageListenerContainer(containerProperties, consumerFactory);
        container.start();
    }

}
