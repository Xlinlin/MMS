package org.xiao.message.kafka;

import org.springframework.kafka.core.KafkaTemplate;

import org.xiao.message.producer.ProducerFactory;

public class TestKafkaProduce
{
    public static void main(String[] args) throws Exception
    {
        String servers = "192.168.206.208:9092";
        //		String groupId="order-topci";
        String topic = "order-test";
        KafkaTemplate<String, String> kafkaTemplate = ProducerFactory.createKafkaTemplate(servers, topic, null);
        for (int i = 0; i < 100; i++)
        {
            kafkaTemplate.send(topic, "test", "nihao");
            Thread.sleep(1000);
        }

        System.out.println("localhost=====");
    }
}
