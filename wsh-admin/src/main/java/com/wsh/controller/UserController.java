package com.wsh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsh.annotation.SystemLog;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.UserDto;
import com.wsh.domain.entity.Role;
import com.wsh.domain.entity.User;
import com.wsh.domain.entity.UserRole;
import com.wsh.domain.vo.PageVo;
import com.wsh.domain.vo.UserRoleVo;
import com.wsh.service.RoleService;
import com.wsh.service.UserRoleService;
import com.wsh.service.UserService;
import com.wsh.utils.BeanCopyUtils;
import com.wsh.utils.SecurityUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 查询用户列表
     *
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult page(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userName), User::getUserName, userName);
        queryWrapper.eq(StringUtils.hasText(phonenumber), User::getPhonenumber, phonenumber);
        queryWrapper.eq(StringUtils.hasText(status), User::getStatus, status);
        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Page<User> userPage = userService.page(page, queryWrapper);
        PageVo vo = new PageVo(userPage.getRecords(), userPage.getTotal());
        return ResponseResult.okResult(vo);
    }

    /**
     * 添加用户
     *
     * @param userDto
     * @return
     */
    @PostMapping
    @Transactional
    @SystemLog(businessName = "后台-添加用户")
    public ResponseResult addUser(@RequestBody UserDto userDto) {
        List<User> list = userService.list();
        // 注意：新增用户时注意密码加密存储。
        // 用户名不能为空，否则提示：必需填写用户名
        // 用户名必须之前未存在，否则提示：用户名已存在
        Set<String> names = new HashSet<>();
        // 手机号必须之前未存在，否则提示：手机号已存在
        Set<String> phones = new HashSet<>();
        // 邮箱必须之前未存在，否则提示：邮箱已存在
        Set<String> emails = new HashSet<>();
        list.forEach(item -> {
            names.add(item.getUserName());
            phones.add(item.getPhonenumber());
            emails.add(item.getEmail());
        });
        if (names.contains(userDto.getUserName())) {
            return ResponseResult.errorResult(410, "用户名已注册");
        } else if (StringUtils.hasText(userDto.getPhonenumber()) && phones.contains(userDto.getPhonenumber())) {
            return ResponseResult.errorResult(410, "手机号已注册");
        } else if (StringUtils.hasText(userDto.getEmail()) && emails.contains(userDto.getEmail())) {
            return ResponseResult.errorResult(410, "邮箱已注册");
        }
        //1.加密密码
        String encode = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encode);
        //3.添加用户
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        userService.save(user);
        //2.添加角色user_role，需要user_id
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName());
        User one = userService.getBaseMapper().selectOne(queryWrapper);

        List<UserRole> userRoles = new ArrayList<>();
        userDto.getRoleIds().forEach(i -> userRoles.add(new UserRole(one.getId(), i)));
        userRoleService.saveBatch(userRoles);


        return ResponseResult.okResult("添加用户成功");

    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @SystemLog(businessName = "后台-删除用户")
    public ResponseResult deleteById(@PathVariable Long id) {
        if (SecurityUtils.getUserId().equals(id)) {
            return ResponseResult.errorResult(410, "不能删除当前操作用户");
        }
        userService.removeById(id);
        return ResponseResult.okResult("删除用户成功");
    }

    /**
     * 根据id回显
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable Long id) {
        //1.所有的角色列表
        List<Role> roles = roleService.list();
        //2.用户关联的角色列表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        List<Long> roleIds = new ArrayList<>();
        List<UserRole> userRoleList = userRoleService.list(queryWrapper);
        userRoleList.forEach(i -> roleIds.add(i.getRoleId()));
        //3.用户本身
        User user = userService.getById(id);
        //3.封装vo
        UserRoleVo vo = new UserRoleVo(roleIds, roles, user);
        return ResponseResult.okResult(vo);
    }

    /**
     * 更新用户信息
     *
     * @param userDto
     * @return
     */
    @PutMapping
    @SystemLog(businessName = "后台-更新用户")
    public ResponseResult update(@RequestBody UserDto userDto) {
        User old = userService.getById(userDto.getId());
        //1.手机邮箱不可重复
        List<User> list = userService.list();
        Set<String> phones = new HashSet<>();
        // 邮箱必须之前未存在，否则提示：邮箱已存在
        Set<String> emails = new HashSet<>();
        list.forEach(item -> {
            phones.add(item.getPhonenumber());
            emails.add(item.getEmail());
        });
        // 当前用户除去
        emails.remove(old.getEmail());
        phones.remove(old.getPhonenumber());
        if (StringUtils.hasText(userDto.getPhonenumber()) && phones.contains(userDto.getPhonenumber())) {
            return ResponseResult.errorResult(410, "手机号已注册");
        } else if (StringUtils.hasText(userDto.getEmail()) && emails.contains(userDto.getEmail())) {
            return ResponseResult.errorResult(410, "邮箱已注册");
        }
        //2.更新user_role删旧存新
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userDto.getId());
        userRoleService.remove(queryWrapper);

        List<UserRole> userRoleList = new ArrayList<>();
        userDto.getRoleIds().forEach(i -> userRoleList.add(new UserRole(userDto.getId(), i)));
        userRoleService.saveBatch(userRoleList);
        //3.更新user
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(User::getId, user.getId());
        userService.update(user, userQuery);

        return ResponseResult.okResult("更新用户成功");
    }
}
