package com.bluelion.usercenter.mapper;

import com.bluelion.usercenter.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {

    void addUser();

    User login(String username, String password);

    User detail(String account);
}
