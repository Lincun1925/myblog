package com.wsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-07-31 10:18:04
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
