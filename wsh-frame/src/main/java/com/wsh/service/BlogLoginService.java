package com.wsh.service;

import com.wsh.domain.ResponseResult;
import com.wsh.domain.entity.User;


public interface BlogLoginService {
    ResponseResult login(User user);
}
