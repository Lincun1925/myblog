package com.wsh.controller;

import com.wsh.annotation.SystemLog;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.entity.User;
import com.wsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "前台-更新用户")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "前台-注册用户")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}