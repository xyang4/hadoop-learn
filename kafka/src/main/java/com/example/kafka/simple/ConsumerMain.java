package com.example.kafka.simple;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @author xYang
 * @date 2019/10/17 0017 13:58
 * @purchase kafka 简单消费者
 */
public class ConsumerMain {
    /*
        - 自动提交 offset
        - 手动提交 offset
            1 同步提交 offset
            2 异步提交 offset
            3 数据漏消费和重复消费分析
     */
    public static void main(String[] args) {

        // 1 配置文件
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "aly:9092");
        // 开启自动提交,
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000); // 提交offset时间间隔
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "G_T_Simple"); // 消费者组
        // 注意只有新组或者offset丢失时，才会初始化offset的值，它默认是最新的。因此新加的组默认都是从最新的消费位置开始消费
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 这里手动设置从最旧的数据开始消费

        // 2 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 3 订阅主题
        consumer.subscribe(Arrays.asList("t_NO.1", "t_NO.2", "scala_g"));

        while (true) {

            ConsumerRecords<String, String> record = consumer.poll(Duration.ofSeconds(10)); // 批量获取

            for (ConsumerRecord<String, String> consumerRecord : record) {
                System.out.println(" 分区：" + consumerRecord.partition() + " 偏移量：" + consumerRecord.offset() + " key： " + consumerRecord.key() + " value：" + consumerRecord.value());
            }

            // ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG = false 时需要手动提交，分异步(commitAsync)与同步(commitSync 当前线程阻塞，直到offset提交成功)
            // consumer.commitSync();

            //异步提交
            consumer.commitAsync((Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) -> {
                if (exception != null) {
                    System.err.println("Commit failed for offsets " + offsets);
                }
            });
        }
    }
}
