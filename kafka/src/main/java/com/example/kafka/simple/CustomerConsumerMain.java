package com.example.kafka.simple;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * @author xYang
 * @date 2019/10/17 0017 17:16
 * @purchase
 */
@Slf4j
public class CustomerConsumerMain {
    /*
        - Rebalace : 当订阅主题的分区发生变化所做的分区的重新分配
        - 自定义存储 offset
     */

    private static Map<TopicPartition, Long> currentOffset = new HashMap<>();

    public static void main(String[] args) {
        // 1 配置文件
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "aly:9092");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // 关闭自动提交
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "zxyGroup1"); // 消费者组

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Arrays.asList("t_NO.3", "t_NO.4"), new ConsumerRebalanceListener() {

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                commitOffset(currentOffset);
            }

            /**
             * Rebalance后，重新分配offset
             */
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                currentOffset.clear();
                for (TopicPartition partition : partitions) {
                    consumer.seek(partition, getOffset(partition));// 定位到最近提交的offset的位置，继续消费
                }
            }
        });

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100); // 批量获取

            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(" 分区：" + consumerRecord.partition() + " 偏移量：" + consumerRecord.offset() + " key： " + consumerRecord.key() + " value：" + consumerRecord.value());
                currentOffset.put(new TopicPartition(consumerRecord.topic(), consumerRecord.partition()), consumerRecord.offset());
                log.info("CurrentOffset :{}.", currentOffset.toString());
            }

            // 6 提交offset
            commitOffset(currentOffset); //自定义的提交offset方法
        }

    }

    /**
     * 获取 offset
     */
    private static long getOffset(TopicPartition partition) {
        // 可以发现所有分区都从10开始，表名我们在启动新节点时，会进行一次Rebalance
        return 10;
    }

    /**
     * 提交 offset
     */
    private static void commitOffset(Map<TopicPartition, Long> currentOffset) {
        // 将偏移量提交到非易失环境中
        log.info("Commit Offset:{}.", currentOffset);
    }
}
