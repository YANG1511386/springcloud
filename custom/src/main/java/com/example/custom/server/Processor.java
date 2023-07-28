package com.example.custom.server;

import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

public interface Processor extends Source, Sink {
}

