package org.example.kafkaspring.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageProducer {

    private final String TOPIC = "practice-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String[] messages) {

        for(String message: messages) {
            String key = message.split(":")[0];
            String value = message.split(":")[1];
            int partition = Integer.parseInt(String.valueOf(key.charAt(key.length() - 1))) - 1;
            this.kafkaTemplate.send(TOPIC, partition, key, message);
        }
    }
}
