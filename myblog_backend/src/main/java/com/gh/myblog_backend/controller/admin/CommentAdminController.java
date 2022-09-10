package com.gh.myblog_backend.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.*;
import com.gh.myblog_backend.service.BlogService;
import com.gh.myblog_backend.service.CommentService;
import com.gh.myblog_backend.service.EssayService;
import com.gh.myblog_backend.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
@RequestMapping("/theAdminOfMyBlogGh/comment")
@CrossOrigin
@Api(description = "评论后台管理")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private EssayService essayService;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/getCommentCount")
    @ApiOperation(value = "评论总数")
    public Result getCommentCount(){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        int count = commentService.count(wrapper);
        return new Result(true, StatusCode.OK, "获取评论总数成功", count);
    }

    @GetMapping("getCommentList")
    @ApiOperation(value = "获取最新评论")
    public Result getCommentList(){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time").last("limit 5");
        List<Comment> comments = commentService.list(wrapper);
        return new Result(true, StatusCode.OK, "获取最新评论成功", comments);
    }

    @GetMapping("getCommentListHalfYear")
    @ApiOperation("获取半年内评论列表")
    public Result getCommentListHalfYear(){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add( Calendar.MONTH, -6);
        Date halfYear = calendar.getTime();
        wrapper.between("create_time", halfYear, now);
        List<Comment> comments = commentService.list(wrapper);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Comment comment : comments) {
            Map<String, Object> map = new HashMap<>();
            String title = null;
            map.put("comment", comment);
            if (comment.getBlogId() != null) {
                Blog blog = blogService.getById(comment.getBlogId());
                title = blog.getTitle();
            }
            if (comment.getEssayId() != null){
                Essay essay = essayService.getById(comment.getEssayId());
                title = essay.getTitle();
            }
          
            map.put("title", title);
            list.add(map);
        }
        return new Result<>(true, StatusCode.OK, "获取半年评论成功", list);
    }


}

