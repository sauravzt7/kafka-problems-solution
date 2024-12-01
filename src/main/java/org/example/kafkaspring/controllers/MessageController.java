package org.example.kafkaspring.controllers;

import org.example.kafkaspring.services.MessageProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private MessageProducer messageProducer;

    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String[] messages) {
        messageProducer.sendMessage(messages);
        return "Message sent" + messages.length;
    }


}
