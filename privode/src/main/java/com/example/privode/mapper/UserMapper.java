package com.example.privode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.privode.pojo.user;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<user> {
}
