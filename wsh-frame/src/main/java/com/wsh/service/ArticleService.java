package com.wsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.AddArticleDto;
import com.wsh.domain.entity.Article;
import com.wsh.domain.vo.PageVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult<PageVo> pageArticleList(Integer pageNum, Integer pageSize, String title, String summary);
}
