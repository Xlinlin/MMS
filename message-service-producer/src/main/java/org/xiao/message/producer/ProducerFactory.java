package org.xiao.message.producer;

import java.util.HashMap;
import java.util.Map;

import org.xiao.message.service.MessageSendFeedbackService;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class ProducerFactory
{

    public static KafkaTemplate<String, String> createKafkaTemplate(String servers, String topic,
            MessageSendFeedbackService messageSendFeedbackService)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("bootstrap.servers", servers);
        map.put("batch.size", "16384");
        map.put("linger.ms", "1");
        map.put("buffer.memory", "33554432");
        map.put("key.serializer", StringSerializer.class);
        map.put("value.serializer", StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(map);
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerListener(new KafkaProducerListener(messageSendFeedbackService));
        kafkaTemplate.setDefaultTopic(topic);
        producerFactory.start();
        return kafkaTemplate;
    }

}
