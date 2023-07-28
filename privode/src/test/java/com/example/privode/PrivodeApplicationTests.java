package com.example.privode;

import com.example.privode.server.nettyserver.NettyServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;

@SpringBootTest
class PrivodeApplicationTests {
@Autowired
private NettyServer nettyServer;
    @Test
    void contextLoads() {

    }

}
