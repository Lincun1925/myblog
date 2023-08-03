package com.wsh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.RoleStatusDto;
import com.wsh.domain.entity.Role;
import com.wsh.domain.entity.User;
import com.wsh.domain.vo.PageVo;
import com.wsh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     *
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(@RequestParam Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);
        queryWrapper.eq(StringUtils.hasText(status), Role::getStatus, status);
        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Page<Role> rolePage = roleService.page(page, queryWrapper);
        PageVo vo = new PageVo(rolePage.getRecords(), rolePage.getTotal());
        return ResponseResult.okResult(vo);
    }

    /**
     * 更改角色状态
     *
     * @param roleStatusDto
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult updateById(@RequestBody RoleStatusDto roleStatusDto) {
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        roleService.updateById(role);
        return ResponseResult.okResult("操作成功");
    }

    /**
     * 查询角色信息
     * @return
     */
    @GetMapping("/listAllRole")
    public ResponseResult list() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, 0);
        return ResponseResult.okResult(roleService.list(queryWrapper));
    }
}
