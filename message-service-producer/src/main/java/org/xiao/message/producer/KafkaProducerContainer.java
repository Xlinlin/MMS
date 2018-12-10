package org.xiao.message.producer;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducerContainer
{

    static ConcurrentHashMap<String, KafkaTemplate<String, String>> cache = new ConcurrentHashMap<String, KafkaTemplate<String, String>>();

    public static void addKafkaTemplate(String topic, KafkaTemplate<String, String> template)
    {
        cache.put(topic, template);
    }

    public static void removeKafkaTemplate(String topic)
    {
        KafkaTemplate<String, String> template = cache.remove(topic);
        if (null != template)
        {
            template.flush();
            template = null;
        }
    }

    public static KafkaTemplate<String, String> getKafkaTemplate(String topic)
    {
        return cache.get(topic);
    }
}
