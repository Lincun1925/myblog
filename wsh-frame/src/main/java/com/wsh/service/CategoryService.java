package com.wsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.CategoryListDto;
import com.wsh.domain.entity.Category;
import com.wsh.domain.vo.CategoryVo;
import com.wsh.domain.vo.PageVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-07-27 21:46:07
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult<PageVo> pageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    ResponseResult addCategory(CategoryListDto categoryListDto);
}
