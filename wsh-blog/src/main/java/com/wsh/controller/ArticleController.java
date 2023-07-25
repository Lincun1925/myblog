package com.wsh.controller;

import com.wsh.domain.ResponseResult;
import com.wsh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult test() {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
}
