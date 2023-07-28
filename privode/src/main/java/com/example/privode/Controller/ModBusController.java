package com.example.privode.Controller;

import com.example.privode.server.modbusmaster.ModbusMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModBusController {
    @Autowired
    private ModbusMaster master;
    @GetMapping("/modebus/readcode")
    public String[] readcode(int startdata,int count) throws Exception {
        return master.redecode(startdata,count);
    }
}
