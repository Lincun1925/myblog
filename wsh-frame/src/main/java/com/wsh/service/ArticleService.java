package com.wsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
}
