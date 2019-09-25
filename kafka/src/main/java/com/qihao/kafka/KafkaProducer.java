package com.qihao.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/17 16:40
 */

@Component
public class KafkaProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;


    public void send(String message){
        kafkaTemplate.send("topic_partition_java", new Random().nextInt(3),message);
    }

}
