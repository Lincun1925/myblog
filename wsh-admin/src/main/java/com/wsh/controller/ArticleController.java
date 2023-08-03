package com.wsh.controller;

import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.AddArticleDto;
import com.wsh.domain.dto.ArticleDto;
import com.wsh.domain.dto.CategoryListDto;
import com.wsh.domain.vo.PageVo;
import com.wsh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article) {
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> list(@RequestParam Integer pageNum, Integer pageSize, String title, String summary) {
        return articleService.pageArticleList(pageNum, pageSize, title, summary);
    }


}