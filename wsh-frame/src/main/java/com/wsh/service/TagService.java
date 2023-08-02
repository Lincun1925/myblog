package com.wsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.TagListDto;
import com.wsh.domain.entity.Tag;
import com.wsh.domain.vo.PageVo;
import com.wsh.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-08-02 10:51:08
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    List<TagVo> listAllTag();

}
