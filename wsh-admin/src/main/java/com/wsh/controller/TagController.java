package com.wsh.controller;

import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.TagListDto;
import com.wsh.domain.entity.Tag;
import com.wsh.domain.vo.PageVo;
import com.wsh.enums.AppHttpCodeEnum;
import com.wsh.service.TagService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    public ResponseResult add(@RequestBody TagListDto tagListDto) {
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult remove(@PathVariable Integer id) {
        if (tagService.removeById(id)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(500, "删除失败");
        }
    }

    @GetMapping("/{id}")
    public ResponseResult<Tag> getById(@PathVariable Integer id) {
        return ResponseResult.okResult(tagService.getById(id));
    }

    @PutMapping
    public ResponseResult update(@RequestBody Tag tag) {
        if (tagService.updateById(tag)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(500, "修改错误");
        }

    }
}