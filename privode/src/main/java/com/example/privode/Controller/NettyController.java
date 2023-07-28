package com.example.privode.Controller;

import com.example.privode.server.nettyserver.NettyServerHandler;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.example.privode.server.nettyserver.NettyServerHandler.CHANNEL_MAP;
import static com.example.privode.server.nettyserver.NettyServerHandler.CHANNEL_MAPNAME;

@RestController
@Slf4j
public class NettyController {
    @GetMapping("/pushnetty")
    public void testnetty(){
        try {
            new NettyServerHandler().channelWrite(CHANNEL_MAPNAME.get("nett"),"nihao");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

}
