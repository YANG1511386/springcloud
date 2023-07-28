package com.example.custom.server;

import com.example.custom.fallback.ServerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "spring-cloud-alibaba-pro",fallback = ServerClientFallback.class)
public interface EchoService {

    /**
     * 调用服务提供方的输出接口
     *
     * @param goodsId 用户输入
     * @return
     */
    @GetMapping(value = "/user/testnacos")
    String echo(@RequestParam(value = "goodsId") String goodsId);



    /**
     * 调用服务提供方的同步读取kespserve方法
     *
     * @return
     */
    @GetMapping(value = "/nacos-producer/opcua/synlistready")
    String synready(@RequestParam(value = "id") String id);
}
