package com.wsh.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.CategoryListDto;
import com.wsh.domain.dto.TagListDto;
import com.wsh.domain.entity.Category;
import com.wsh.domain.entity.Tag;
import com.wsh.domain.vo.CategoryVo;
import com.wsh.domain.vo.ExcelCategoryVo;
import com.wsh.domain.vo.PageVo;
import com.wsh.enums.AppHttpCodeEnum;
import com.wsh.service.CategoryService;
import com.wsh.utils.BeanCopyUtils;
import com.wsh.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> categoryList(@RequestParam Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        return categoryService.pageCategoryList(pageNum, pageSize, categoryListDto);
    }


    @PostMapping
    public ResponseResult add(@RequestBody CategoryListDto categoryListDto) {
        return categoryService.addCategory(categoryListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult remove(@PathVariable Integer id) {
        if (categoryService.removeById(id)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(500, "删除失败");
        }
    }

    @GetMapping("/{id}")
    public ResponseResult<Tag> getById(@PathVariable Integer id) {
        return ResponseResult.okResult(categoryService.getById(id));
    }

    @PutMapping
    public ResponseResult update(@RequestBody Category category) {
        if (categoryService.updateById(category)) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(500, "修改错误");
        }
    }
}