package com.example.privode.server.serverimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.privode.mapper.UserMapper;
import com.example.privode.pojo.user;
import com.example.privode.server.Userservice;
import org.springframework.stereotype.Service;

@Service
public class Userserviceimpl extends ServiceImpl<UserMapper, user> implements Userservice {
}
