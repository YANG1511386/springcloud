package com.example.custom.fallback;

import com.example.custom.server.EchoService;
import org.springframework.stereotype.Component;

@Component
public class ServerClientFallback implements EchoService {
    @Override
    public String echo(String goodsId) {
        return "提供者无法工作";
    }

    @Override
    public String synready(String id) {
        return "提供者无法工作";
    }
}
