package com.example.privode.Controller;


import com.example.privode.client.opcuaclient.OpcuaClient;
import com.example.privode.util.Changetype;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/nacos-producer")
public class OpcuaController {
    @Autowired
    OpcuaClient client;

    /**
     * 连接客户端接口
     * @param serverUrl
     * @throws Exception
     */
    @GetMapping("/opcua/connect")
    public void connect(@RequestParam String serverUrl) throws Exception {
        client.createClientdemo(serverUrl);
    }

    /**
     * 断开客户端接口
     * @throws Exception
     */
    @GetMapping("/opcua/disconnect")
    public void disconnect() throws Exception {
        client.disconnect();
    }

    /**
     * 同步读
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/opcua/synready")
    public String synready( @RequestParam String id) throws Exception {
        return client.readNode(id);
    }

    /**
     *批量读
     * @return
     * @throws Exception
     */
    @GetMapping("/opcua/synlistready")
    public List<String> synlistready(@RequestParam List<String> id) throws Exception {
        return client.readListNode(id);
    }

    /**
     * 异步读
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/opcua/asyready")
    public String asyready(@RequestParam String id) throws Exception {
        return client.asyreadNode(id);
    }


    /**
     * 同步写
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/opcua/synwrite")
    public String synwrite(@RequestParam String id,@RequestParam String value,@RequestParam String paramtype) throws Exception {

        return client.writeNodeValue(id,value,paramtype)  ;
    }

    /**
     *批量同步写
     * @return
     * @throws Exception
     */
    @GetMapping("/opcua/synlistwrite")
    public List<Long> synlistwrite(@RequestParam List<String> id,@RequestParam List<Object> value,@RequestParam String paramtype) throws Exception {
        return client.writelistNodeValue(id,value,paramtype)  ;
    }

    /**
     *异步写
     * @param id
     * @param value
     * @param paramtype
     * @return
     * @throws Exception
     */
    @GetMapping("/opcua/asywrite")
    public String asywrite(@RequestParam String id,@RequestParam String value,@RequestParam String paramtype) throws Exception {
        Object data = Changetype.createData(paramtype, value);
        client.asywriteNodeValue(id,data);
        return  "写入成功" ; }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */

    @GetMapping("/opcua/subtag")
    public String subtag(@RequestParam List<String> id) throws Exception {
        for (String subid : id) {
            client.subscribe(subid);
        }
        return "订阅成功";
    }


    /**
     *
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping("/opcua/asyreadlistNode")
    public String asyreadlistNode(@RequestParam List<String> id) throws Exception {
        client.asyreadlistNode(id);
        return  "异步读取成功" ;
    }


    /**
     *异步批量写
     * @return
     * @throws Exception
     */
    @GetMapping("/opcua/asylistwrite")
    public String asylistwrite(@RequestParam List<String> id,@RequestParam List<Object> value,@RequestParam String paramtype) throws Exception {
        client.asywriteListNodeValue(id,value,paramtype);
        return  "写入成功" ; }


//    /**
//     * 测试时间
//     * @return
//     */
//    @GetMapping("/opcua/testtime")
//    public double testtime(int x,int y) throws Exception {
//        return  opcua.testtime(x,y); }
}
