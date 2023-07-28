package com.example.privode;

import com.example.privode.server.nettyserver.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.net.InetSocketAddress;

@SpringBootApplication
@EnableFeignClients
public class PrivodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivodeApplication.class, args);
        NettyServer nettyServer = new NettyServer();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",1024);
        nettyServer.start(inetSocketAddress);
    }

}
