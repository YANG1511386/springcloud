package com.example.privode.client.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.privode.client.nettyclient.NettyClientHandler.connect;

@Component
@Slf4j
public class NettyClient{
    public static Bootstrap  start() throws InterruptedException {
        Bootstrap  Bootstrap  = new Bootstrap()
                .group(new NioEventLoopGroup()) // 1
                .channel(NioSocketChannel.class) // 2
                .handler(new NettyClientChannelInitializer());
        return Bootstrap ;
    }


//
//    public static void main(String[] args) throws Exception {
//        connect();
//        try {
//            Thread.sleep(3000);  // 3000 milliseconds = 3 seconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new NettyClientHandler().channelWrite("lalal");
//        // Handle the case where the context is not yet initialized or connection is closed.
//    }
}
