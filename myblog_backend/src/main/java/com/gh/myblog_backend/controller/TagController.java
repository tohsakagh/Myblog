package com.gh.myblog_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gh.myblog_backend.entity.*;
import com.gh.myblog_backend.entity.po.FullBlog;
import com.gh.myblog_backend.service.BlogService;
import com.gh.myblog_backend.service.TagService;
import com.gh.myblog_backend.service.TypeService;
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
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/front/tag")
@CrossOrigin
@Api(value = "前台标签")
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @GetMapping("{id}/{current}/{limit}")
    @ApiOperation(value = "根据标签id获取博客")
    public Result getBlogList(@PathVariable String id,
                              @PathVariable long current,
                              @PathVariable long limit){

        List<String> blogIds =  tagService.getBlogIdList(id);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.in("id", blogIds);
        Page<Blog> blogPage = new Page<>(current, limit);
        blogService.page(blogPage, wrapper);
        List<Blog> blogs = blogPage.getRecords();
        long total = blogPage.getTotal();
        List<FullBlog> records = new ArrayList<>();
        for (Blog blog : blogs) {
            FullBlog fullBlog = new FullBlog();
            BeanUtils.copyProperties(blog, fullBlog);
            QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
            typeQueryWrapper.eq("id", blog.getTypeId()).select("name");
            Type type = typeService.getOne(typeQueryWrapper);
            fullBlog.setTypeName(type.getName());
            records.add(fullBlog);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("content", records);
        map.put("totalElements", total);
        return new Result<>(true, StatusCode.OK, "获取博客成功", map);
    }

    @GetMapping("getAllTag")
    @ApiOperation("获取全部标签列表")
    public Result getAllType(){
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("blog_count");
        List<Tag> list = tagService.list(null);
        return new Result(true, StatusCode.OK, "获取博客全部分类成功", list);
    }

    @GetMapping("getTagList")
    @ApiOperation("获取博客数量最多的10个标签")
    public Result getTagList(){
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("blog_count").last("limit 10");
        List<Tag> list = tagService.list(wrapper);
        return new Result(true, StatusCode.OK, "获取博客全部分类成功", list);
    }

    @PostMapping("createTag")
    @ApiOperation(value = "添加标签")
    public Result createTag(@RequestBody Map<String, Tag> para){
        Tag tag = para.get("tag");
        boolean save = tagService.save(tag);
        if (save){
            QueryWrapper<Tag> wrapper = new QueryWrapper<>();
            wrapper.eq("name", tag.getName());
            Tag one = tagService.getOne(wrapper);
            return new Result<>(true, StatusCode.OK, "添加成功", one);
        }else {
            return new Result<>(true, StatusCode.ERROR, "添加失败", null);
        }
    }
}

