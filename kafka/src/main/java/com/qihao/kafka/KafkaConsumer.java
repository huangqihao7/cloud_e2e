package com.qihao.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/17 16:51
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "topic_partition_java", groupId = "kafka_gid")
    public void listener(ConsumerRecord record){
        Optional value = Optional.ofNullable(record.value());
        if(value.isPresent()){
            System.out.println(value.get());
        }
    }
}
