package com.wsh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.entity.Link;
import com.wsh.domain.vo.PageVo;
import com.wsh.service.LinkService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 友链查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult page(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name), Link::getName, name);
        queryWrapper.eq(StringUtils.hasText(status), Link::getStatus, status);
        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Page<Link> linkPage = linkService.page(page, queryWrapper);
        PageVo vo = new PageVo(linkPage.getRecords(), linkPage.getTotal());
        return ResponseResult.okResult(vo);
    }

    /**
     * 新增友链
     *
     * @param link
     * @return
     */
    @PostMapping
    public ResponseResult add(@RequestBody Link link) {
        linkService.save(link);
        return ResponseResult.okResult("新增成功");
    }

    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable Long id) {
        return ResponseResult.okResult(linkService.getById(id));
    }

    @PutMapping
    public ResponseResult update(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult("修改成功");
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteById(@PathVariable String ids) {
        List<Long> id = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        linkService.removeByIds(id);
        return ResponseResult.okResult("删除成功");
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult updateStatusById(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult("修改成功");
    }
}
