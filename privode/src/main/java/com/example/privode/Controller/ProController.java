package com.example.privode.Controller;

import com.example.privode.server.IHhChainApiInfoService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
//@Api(value = "用户接口", tags = {"用户接口"})
public class ProController {
    @Autowired
    private IHhChainApiInfoService iHhChainApiInfoService;
//    @ApiOperation("查询用户")
    @GetMapping("/testnacos")
    public String testnacos(@RequestParam String goodsId){
        return iHhChainApiInfoService.testnacos(goodsId);
    }
}
