package com.gh.myblog_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gh.myblog_backend.entity.Blog;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.entity.Type;
import com.gh.myblog_backend.entity.po.FullBlog;
import com.gh.myblog_backend.service.BlogService;
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
@RequestMapping("/front/type")
@CrossOrigin
@Api(description = "前台分类")
public class TypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("{id}/{current}/{limit}")
    @ApiOperation("根据分类获取博客列表")
    public Result getBlogListByTypeId(@PathVariable String id,
                                      @PathVariable long current,
                                      @PathVariable long limit){
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("type_id", id);
        Page<Blog> blogPage = new Page<>(current, limit);
        blogService.page(blogPage, wrapper);
        List<Blog> blogs = blogPage.getRecords();
        long total = blogPage.getTotal();
        List<FullBlog> records = new ArrayList<>();
        QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.eq("id", id).select("name");
        Type type = typeService.getOne(typeQueryWrapper);
        for (Blog blog : blogs) {
            FullBlog fullBlog = new FullBlog();
            BeanUtils.copyProperties(blog, fullBlog);
            fullBlog.setTypeName(type.getName());
            records.add(fullBlog);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("content", records);
        map.put("totalElements", total);
        return new Result<>(true, StatusCode.OK, "获取博客成功", map);
    }

    @GetMapping("getAllType")
    @ApiOperation("获取全部分类")
    public Result getAllType(){

      QueryWrapper<Type> wrapper = new QueryWrapper<>();
      wrapper.orderByDesc("blog_count");
        List<Type> list = typeService.list(wrapper);
        return new Result(true, StatusCode.OK, "获取博客全部分类成功",list);
    }

    @GetMapping("getTypeList")
    @ApiOperation("获取6个博客数量最多的分类")
    public Result getTypeList(){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("blog_count").last("limit 6");
        List<Type> list = typeService.list(wrapper);
        return new Result(true, StatusCode.OK, "获取博客全部分类成功",list);
    }
}

