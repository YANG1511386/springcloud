package com.example.privode.server.mqserver;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannels {
    @Output("output-channel")
    MessageChannel outputChannel();

    @Input("input-channel")
    SubscribableChannel inputChannel();
}
