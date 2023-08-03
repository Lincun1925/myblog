package com.wsh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.entity.Menu;
import com.wsh.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单列表
     * @param status
     * @param menuName
     * @return
     */
    @GetMapping("/list")
    public ResponseResult list(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Menu::getStatus, status);
        queryWrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        return ResponseResult.okResult(menuService.list(queryWrapper));
    }
}
