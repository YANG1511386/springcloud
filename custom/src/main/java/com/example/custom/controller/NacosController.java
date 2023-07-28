package com.example.custom.controller;

import com.example.custom.server.EchoService;
import com.example.custom.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class NacosController {
    @Autowired
    private EchoService echoService;
    @Autowired
    private RedisUtil redisUtil;
    @GetMapping("/hello")
    private String hello(@RequestParam String goodsid){
        return echoService.echo(goodsid);
    }

    @GetMapping("/synready")
    private String synready(@RequestParam String id){
        String synready = echoService.synready(id);
        //存入reids
        redisUtil.set("opcua",synready);
    return synready;
    }
}
