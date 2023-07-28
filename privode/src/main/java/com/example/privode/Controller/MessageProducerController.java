package com.example.privode.Controller;

import com.example.privode.server.mqserver.MessageChannels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(MessageChannels.class)
public class MessageProducerController {

    @Autowired
    private MessageChannels messageChannels;

    @PostMapping("/send")
    @SendTo("output-channel")
    public String sendMessage(@RequestBody String message) {
        messageChannels.outputChannel().send(MessageBuilder.withPayload(message).build());
        return "Message sent: " + message;
    }
}

