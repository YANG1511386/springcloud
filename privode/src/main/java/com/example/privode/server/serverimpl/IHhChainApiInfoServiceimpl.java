package com.example.privode.server.serverimpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.privode.pojo.user;
import com.example.privode.server.IHhChainApiInfoService;
import com.example.privode.server.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IHhChainApiInfoServiceimpl implements IHhChainApiInfoService {
    @Autowired
    Userservice userservice;
    @Override
    @Transactional
    public String testnacos(String name) {
        UpdateWrapper<user> objectUpdateWrapper = new UpdateWrapper<>();
        objectUpdateWrapper.eq("userid",1);
        objectUpdateWrapper.set("name",name);
        boolean update = userservice.update(objectUpdateWrapper);
//        throw new NullPointerException("This is a simulated NullPointerException");
        return "调用成功";
    }
}
