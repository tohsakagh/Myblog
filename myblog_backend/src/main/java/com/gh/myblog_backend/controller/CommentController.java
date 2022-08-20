package com.gh.myblog_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.Comment;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.entity.User;
import com.gh.myblog_backend.service.CommentService;
import com.gh.myblog_backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/front/comment")
@CrossOrigin
@Api(description = "前台评论")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping("addComment")
    @ApiOperation(value = "发表评论")
    public Result addComment(@RequestBody Map<String,Object> para){
        String content= (String) para.get("content");
        String blogId= (String) para.get("blogId");
        String essayId= (String) para.get("essayId");
        String projectId= (String) para.get("projectId");
        String userId= (String) para.get("userId").toString();
        String parentId= (String) para.get("parentId").toString();
        User user = userService.getById(userId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setAvatar(user.getAvatar());
        comment.setNickname(user.getNickname());
        if (blogId != null){
            comment.setBlogId(blogId);
        }
        if (essayId != null){
            comment.setEssayId(essayId);
        }
        if (projectId != null){
            comment.setProjectId(projectId);
        }
        if ("1".equals(user.getType())){
            comment.setAdminComment(0);
        }
        if (!"-1".equals(parentId)){
            comment.setParentId(parentId);
        }
        boolean save = commentService.save(comment);
        if (save) {
            return new Result(true, StatusCode.OK, "评论发表成功！", null);
        }else {
            return new Result(false,StatusCode.ERROR,"评论发表失败！",null);
        }
    }

    @GetMapping("delete/{id}")
    @ApiOperation(value = "删除评论")
    private Result delete(@PathVariable String id){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Comment> children = commentService.list(wrapper);
        if (children != null){
            for (Comment child : children) {
                child.setParentId(null);
                commentService.updateById(child);
            }
        }
        boolean remove = commentService.removeById(id);
        if (remove) {
            return new Result(true, StatusCode.OK, "评论删除成功！", null);
        }else {
            return new Result(false, StatusCode.ERROR,"评论删除失败！",null);
        }
    }
}

