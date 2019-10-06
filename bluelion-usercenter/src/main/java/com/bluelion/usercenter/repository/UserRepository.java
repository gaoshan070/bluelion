package com.bluelion.usercenter.repository;

import com.bluelion.usercenter.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User login(String username, String password);

    User detail(String account);
}
