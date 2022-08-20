package com.gh.myblog_backend.controller.theAdminOfMyBlogGh;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
@RequestMapping("/theAdminOfMyBlogGh/blog")
@Api(description = "博客后台管理")
@CrossOrigin
public class BlogAdminController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogTagsService blogTagsService;
    @Autowired
    private TagService tagService;
    @Autowired
    private DevelopmentService developmentService;

    @GetMapping("getAllViewCount")
    @ApiOperation(value = "总阅读量")
    public Result getAllViewCount() {
        int count = blogService.countAllView();
        return new Result<>(true, StatusCode.OK, "获取阅读总量成功", count);
    }

    @GetMapping("getBlogCount")
    @ApiOperation(value = "博客读量")
    public Result getBlogCount() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        int count = blogService.count(wrapper);
        return new Result<>(true, StatusCode.OK, "获取博客总数成功", count);
    }

    @GetMapping("getAppreciateCount")
    @ApiOperation(value = "赞赏总数")
    public Result getgetAppreciateCount() {
        int count = blogService.countAppreciate();
        return new Result<>(true, StatusCode.OK, "获取赞赏总数成功", count);
    }

    @GetMapping("/getRecommendBlogList")
    @ApiOperation(value = "获取最新5篇博客")
    public Result getRecommendBlogList() {
        List<FullBlog> blogTypes = blogService.getRecommendBlogs();
        return new Result(true, StatusCode.OK, "获取推荐博客成功", blogTypes);
    }

    @PostMapping("getBlogList")
    @ApiOperation("博客列表")
    //后期可以自己写sql语句重写该方法
    public Result getBlogList(@RequestBody Map<String, Object> para) {
        int pagenum = (int) para.get("pagenum");
        int pagesize = (int) para.get("pagesize");
        Page<Blog> blogPage = new Page<>(pagenum, pagesize);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        if (para.get("title") != null) {
            wrapper.like("title", (String) para.get("title"));
        }
        if (para.get("typeId") != null) {
            wrapper.eq("type_id", (String) para.get("typeId"));
        }
        blogService.page(blogPage, wrapper);
        List<Blog> blogss = blogPage.getRecords();
        long total = blogPage.getTotal();
        List<FullBlog> records = new ArrayList<>();
        for (Blog blog : blogss) {
            FullBlog fullBlog = new FullBlog();
            BeanUtils.copyProperties(blog, fullBlog);
            QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
            typeQueryWrapper.eq("id", blog.getTypeId()).select("name");
            fullBlog.setTypeName(typeService.getOne(typeQueryWrapper).getName());
            QueryWrapper<BlogTags> blogTagsQueryWrapper = new QueryWrapper<>();
            blogTagsQueryWrapper.eq("blogs_id", blog.getId()).select("tags_id");
            List<BlogTags> blogTags = blogTagsService.list(blogTagsQueryWrapper);
            List<Tag> tags = new ArrayList<>();
            for (int i = 0; i < blogTags.size(); i++) {
                Tag tag = tagService.getById(blogTags.get(i).getTagsId());
                tags.add(tag);
            }
            fullBlog.setTags(tags);
            records.add(fullBlog);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("content", records);
        map.put("totalElements", total);
        return new Result(true, StatusCode.OK, "获取博客列表成功", map);
    }

    @PostMapping("updateBlog")
    @ApiOperation(value = "更新blog")
    public Result updateBlog(@RequestBody Map<String, Blog> para) {
        Blog blog = para.get("blog");
        blogService.updateById(blog);
        return new Result(true, StatusCode.OK, "修改博客成功", null);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除博客")
    public Result deleteBlog(@PathVariable String id) {
        boolean remove = blogService.removeBlogById(id);
        QueryWrapper<Development> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", id);
        boolean remove1 = developmentService.remove(wrapper);
        if (remove && remove1) {
            return new Result(true, StatusCode.OK, "删除博客成功", null);
        } else {
            return new Result(true, StatusCode.ERROR, "删除博客失败", null);
        }
    }

    @PostMapping("/saveBlog")
    @ApiOperation(value = "保存博客")
    public Result saveBlog(@RequestBody Map<String, FullBlog> params) {
        FullBlog fullBlog = params.get("blog");
        System.out.println("*******************************************" + fullBlog.toString());
        blogService.saveFullBlog(fullBlog);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("title", fullBlog.getTitle());
        Blog one = blogService.getOne(wrapper);
        Development development = new Development();
        development.setTask("发布了博客: " + one.getTitle());
        development.setUrl("/blogInfo?id=" + one.getId());
        development.setColor("#1990EB");
        development.setType(2);
        development.setBlogId(one.getId());
        developmentService.save(development);
        return new Result(true, StatusCode.OK, "发布博客成功", null);
    }

    @PostMapping("/updateBlogTag")
    @ApiOperation(value = "更新博客标签")
    public Result updateBlogTag(@RequestBody Map<String, String> params) {
        String blogId = params.get("blogId");
        String tagName = params.get("tagName");

        //若没有该标签则床架并且标签博客数量加1，如有则在直接加1
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("name", tagName);
        int count = tagService.count(wrapper);
        Tag tag;
        if (count <= 0) {
            tag = new Tag();
            tag.setName(tagName);
            tagService.save(tag);
        }
        tag = tagService.getOne(wrapper);
        tag.setBlogCount(tag.getBlogCount() + 1);
        UpdateWrapper<Tag> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", tag.getId());
        tagService.update(tag, updateWrapper);

        BlogTags blogTags = new BlogTags();
        blogTags.setTagsId(tag.getId());
        blogTags.setBlogsId(blogId);
        blogTagsService.save(blogTags);

        return new Result(true, StatusCode.OK, "更新博客标签成功", null);
    }

    @PostMapping("updateType")
    @ApiOperation(value = "更新博客分类")
    public Result updateType(@RequestBody Map<String, Blog> para) {
        Blog blog = para.get("blog");
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id", blog.getId());
        Blog one = blogService.getOne(wrapper);
        if (one.getTypeId() != blog.getTypeId()) {
            QueryWrapper<Type> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("id", one.getTypeId());
            Type one1 = typeService.getOne(wrapper1);
            one1.setBlogCount(one1.getBlogCount() - 1);

            QueryWrapper<Type> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("id", blog.getTypeId());
            Type one2 = typeService.getOne(wrapper2);
            one1.setBlogCount(one2.getBlogCount() + 1);
            boolean update = blogService.updateById(blog);
        }
        return new Result(true, StatusCode.OK, "更新博客分类成功", null);
    }
}

