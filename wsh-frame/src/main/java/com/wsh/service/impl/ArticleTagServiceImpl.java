package com.wsh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wsh.domain.entity.ArticleTag;
import com.wsh.mapper.ArticleTagMapper;
import com.wsh.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-08-02 23:36:32
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
