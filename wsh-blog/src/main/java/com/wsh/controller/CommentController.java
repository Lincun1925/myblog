package com.wsh.controller;

import com.wsh.constants.SystemConstants;
import com.wsh.domain.ResponseResult;
import com.wsh.domain.dto.AddCommentDto;
import com.wsh.domain.entity.Comment;
import com.wsh.service.CommentService;
import com.wsh.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
//@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @ApiOperation(value = "评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小")})
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }

    @PostMapping
    @ApiOperation(value = "添加评论")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto,
                                     @RequestHeader(value = "token", required = true) String token) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }
}