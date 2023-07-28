package com.example.privode.server;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 链路api信息Service接口
 *
 * @author hauchun
 * @date 2023-02-09
 */
//@FeignClient(name = ServiceMode.CHAIN_SERVICE_NAME, path = "/chain/HhChainApiInfo")
//@FeignClient(path = "/chain/HhChainApiInfo")
public interface IHhChainApiInfoService {
    String testnacos(String name);
//    /**
//     * 查询链路api信息
//     *
//     * @param id 链路api信息主键
//     * @return 链路api信息
//     */
//    @GetMapping(value = "/{id}")
//    public void selectHhChainApiInfoById(@PathVariable("id") Long id);
//
//    /**
//     * 查询链路api信息列表
//     * @return 链路api信息集合
//     */
//    @GetMapping("/list")
//    public void selectHhChainApiInfoList(@RequestParam("hhChainApiInfo") String name);

}
