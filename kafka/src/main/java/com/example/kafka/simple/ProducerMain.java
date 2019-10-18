package com.example.kafka.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author xYang
 * @date 2019/10/17 0017 13:58
 * @purchase //TODO 一句话说明
 */
public class ProducerMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1 配置文件
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "aly:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 1); // 重试次数
        // 批量发送
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384); // 16k
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1); // 等待时间
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 2 客户端
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 3 发送数据
        for (int i = 0; i < 10; i++) {
            String topic = "t_NO." + i;
            for (int j = 0; j < 500; j++) {  // 默认按照key的hash值分区
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, "Key_" + j, "v--" + i);
                if (i % 2 == 0) {
                    syncSend(producer, record);
                } else {
                    asyncSend(producer, record);
                }
            }
        }

        // 4 资源回收
        producer.close();
    }

    /**
     * 同步发送
     *
     * @param producer
     * @param producerRecord
     */
    private static void syncSend(KafkaProducer producer, ProducerRecord producerRecord) throws ExecutionException, InterruptedException {
        producer.send(producerRecord).get();
    }

    /**
     * 异步发送
     *
     * @param producer
     * @param producerRecord
     */
    private static void asyncSend(KafkaProducer producer, ProducerRecord producerRecord) {
        producer.send(producerRecord, (RecordMetadata recordMetadata, Exception e) -> {
            if (e == null) {
                System.out.println("数据：" + recordMetadata.toString() + " 分区：" + recordMetadata.partition() + " 偏移量：" + recordMetadata.offset());
            } else {
                e.printStackTrace();
            }
        });
    }
}
