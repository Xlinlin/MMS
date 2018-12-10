package org.xiao.message.consumer.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

/**
 * [简要描述]: kafka streams 测试
 * [详细描述]:
 *
 * @author llxiao
 * @version 1.0, 2018/11/13 10:16
 * @since JDK 1.8
 */
public class KafkaStreamTest
{
    public static void main(String[] args)
    {
        Properties props = new Properties();//streams的配置参数
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.206.211:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        // Kafka Streams DSL。DSL提供的各种算子大部分情况下都可以满足需求
        StreamsBuilder builder = new StreamsBuilder();
        //从Kafka源topic获取数据流
        // KStream即代表了由各个数据记录组成的数据流。
        KStream<String, String> textLines = builder
                .stream("streams-plaintext-input", Consumed.with(stringSerde, stringSerde));

        // 到结果后将其存储为KTable：
        KTable<String, Long> wordCounts = textLines
                // 对得到的KStream进行transformation和aggregation：
                //将数据记录中的大写全部替换成小写：
                //.mapValues(textLine -> textLine.toLowerCase())
                // 将各行数据按空格拆分：
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                // 将value作为新的key：
                //.selectKey((key, word) -> word)
                // aggregation操作前group by key：
                .groupBy((key, word) -> word)
                //计算每个组中的元素个数：
                .count(Materialized.as("Counts"));
        //.count();
        // 最后导入目标topic，其中key为String，value为Long。
        wordCounts.toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);

        Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook")
        {
            @Override
            public void run()
            {
                streams.close();
            }
        });
        streams.start();
    }
}
