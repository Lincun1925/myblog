package com.wsh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.domain.entity.UserRole;
import com.wsh.mapper.UserRoleMapper;
import com.wsh.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-08-03 17:18:56
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
