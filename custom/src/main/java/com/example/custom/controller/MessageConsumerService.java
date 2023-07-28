package com.example.custom.controller;


import com.example.custom.server.MessageChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;


@EnableBinding(MessageChannels.class)
public class MessageConsumerService {
    @StreamListener("input-channel")
    public void handleMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
