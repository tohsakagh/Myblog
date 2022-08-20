package com.gh.myblog_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gh.myblog_backend.entity.*;
import com.gh.myblog_backend.entity.po.FullBlog;
import com.gh.myblog_backend.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/front/blog")
@CrossOrigin
@Api(description = "博客显示")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogTagsService blogTagsService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @GetMapping("getRecommendBlogList")
    @ApiOperation(value = "获取推荐博客")
    public Result getRecommendBlogList() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title").orderByDesc("update_time").last("limit 5");
        List<Blog> blogs = blogService.list(wrapper);
        return new Result<>(true, StatusCode.OK, "获取推建博客成功", blogs);
    }

    @GetMapping("getBlogList/{current}/{limit}")
    @ApiOperation(value = "分页查询博客列表")
    public Result getBNlogList(@PathVariable long current,
                               @PathVariable long limit) {
        Page<Blog> blogPage = new Page<>(current, limit);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        blogService.page(blogPage, wrapper);
        List<Blog> blogs = blogPage.getRecords();
        long total = blogPage.getTotal();
        List<FullBlog> records = new ArrayList<>();
        for (Blog blog : blogs) {
            FullBlog fullBlog = new FullBlog();
            BeanUtils.copyProperties(blog, fullBlog);
            QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
            typeQueryWrapper.eq("id", blog.getTypeId()).select("name");
            Type one = typeService.getOne(typeQueryWrapper);
            fullBlog.setTypeName(one.getName());
            records.add(fullBlog);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("content", records);
        map.put("totalElements", total);
        return new Result<>(true, StatusCode.OK, "获取推建博客成功", map);
    }

    @GetMapping("getFooterBlogList")
    @ApiOperation(value = "页脚获取最新三条博客列表")
    public Result getThreeNewBlog() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time").select("id", "title").last("limit 3");
        List<Blog> blogs = blogService.list(wrapper);
        return new Result(true, StatusCode.OK, "获取最新三条博客成功", blogs);
    }

    @GetMapping("getBlogInfo/{id}")
    @ApiOperation(value = "获取博客信息")
    public Result getBlogInfo(@PathVariable String id) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        Blog blog = blogService.getOne(wrapper);
        FullBlog fullBlog = new FullBlog();
        BeanUtils.copyProperties(blog, fullBlog);
        QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.eq("id", blog.getTypeId()).select("name");
        Type type = typeService.getOne(typeQueryWrapper);
        fullBlog.setTypeName(type.getName());
        QueryWrapper<BlogTags> blogTagsQueryWrapper = new QueryWrapper<>();
        blogTagsQueryWrapper.eq("blogs_id", blog.getId()).select("tags_id");
        List<BlogTags> blogTags = blogTagsService.list(blogTagsQueryWrapper);
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < blogTags.size(); i++) {
            Tag tag = tagService.getById(blogTags.get(i).getTagsId());
            tags.add(tag);
        }
        fullBlog.setTags(tags);
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("blog_id", id).orderByAsc("create_time");
        List<Comment> commentList = commentService.list(commentQueryWrapper);
        for (Comment comment : commentList) {
            if (comment.getParentId() != null){
                QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", comment.getParentId());
                Comment one = commentService.getOne(queryWrapper);
                comment.setParentComment(one);
            }
        }
        fullBlog.setComments(commentList);
        return new Result(true, StatusCode.OK, "获取博客列表成功", fullBlog);
    }
}

