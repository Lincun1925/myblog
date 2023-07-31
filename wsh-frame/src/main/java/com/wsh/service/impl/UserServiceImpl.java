package com.wsh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.domain.entity.User;
import com.wsh.mapper.UserMapper;
import com.wsh.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-31 10:41:35
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
