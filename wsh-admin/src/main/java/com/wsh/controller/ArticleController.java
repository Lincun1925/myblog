package com.wsh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.AddArticleDto;
import com.wsh.domain.dto.ArticleDto;
import com.wsh.domain.dto.CategoryListDto;
import com.wsh.domain.entity.Article;
import com.wsh.domain.entity.ArticleTag;
import com.wsh.domain.vo.PageVo;
import com.wsh.service.ArticleService;
import com.wsh.service.ArticleTagService;
import com.wsh.service.TagService;
import com.wsh.utils.BeanCopyUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article) {
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult<PageVo> list(@RequestParam Integer pageNum, Integer pageSize, String title, String summary) {
        return articleService.pageArticleList(pageNum, pageSize, title, summary);
    }

    /**
     * 查询文章详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult<ArticleDto> getById(@PathVariable Long id) {
        try {
            Article byId = articleService.getById(id);
            //查找文章对应tag
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(ArticleTag::getTagId);
            queryWrapper.eq(ArticleTag::getArticleId, id);
            List<ArticleTag> list = articleTagService.list(queryWrapper);
            //类型转换
            ArticleDto articleDto = BeanCopyUtils.copyBean(byId, ArticleDto.class);
            List<Long> tagList = new ArrayList<>();
            list.forEach(item -> tagList.add(item.getTagId()));
            articleDto.setTags(tagList);
            return ResponseResult.okResult(articleDto);
        } catch (Exception e) {
            return ResponseResult.errorResult(500, "查询文章失败");
        }
    }

    /**
     * 更新文章
     *
     * @param articleDto
     * @return
     */
    @PutMapping
    @Transactional
    public ResponseResult update(@RequestBody ArticleDto articleDto) {
        //TODO 取出标签更新article_tag
//        List<Long> tags = articleDto.getTags();
//        articleTagService.removeById(articleDto.getId());
//        List<ArticleTag> articleTags = new ArrayList<>();
//        tags.forEach(
//                item -> {
//                    ArticleTag articleTag = new ArticleTag(articleDto.getId(), item);
//                    articleTags.add(articleTag);
//                });
//        articleTagService.saveBatch(articleTags);
        //类型转换更新article
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        articleService.update(article, queryWrapper);
        return ResponseResult.okResult("操作成功");

    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable Long id) {
        if (articleService.removeById(id)) {
            return ResponseResult.okResult("删除成功");
        }
        return ResponseResult.errorResult(500, "删除失败");
    }
}