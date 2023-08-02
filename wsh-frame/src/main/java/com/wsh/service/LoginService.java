package com.wsh.service;

import com.wsh.domain.ResponseResult;
import com.wsh.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);
}
