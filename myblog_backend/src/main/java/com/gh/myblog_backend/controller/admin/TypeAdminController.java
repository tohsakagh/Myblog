package com.gh.myblog_backend.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.Blog;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.entity.Type;
import com.gh.myblog_backend.entity.po.TypeBlogs;
import com.gh.myblog_backend.service.BlogService;
import com.gh.myblog_backend.service.TypeService;
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
@RequestMapping("/theAdminOfMyBlogGh/type")
@CrossOrigin
@Api(description = "类型后台管理")
public class TypeAdminController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("getFullTypeList")
    @ApiOperation("类型列表（包括所有博客）")
    public Result getFullTypeList(){
        List<TypeBlogs> list = typeService.listFullType();
        return new Result(true, StatusCode.OK, "获取博客全部分类成功",list);
    }

    @GetMapping("getAllType")
    @ApiOperation("分类列表（不包含博客）")
    public Result getAllType(){
        return new Result(true, StatusCode.OK, "获取博客全部分类成功",typeService.list(null));
    }

    @GetMapping("getTypeById/{id}")
    @ApiOperation("根据id得到typeName")
    public Result getTypeById(@PathVariable String id){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).select("name");
        Type type = typeService.getOne(wrapper);
        return new Result(true, StatusCode.OK, "获取博客分类成功",type.getName());
    }

    @PostMapping("saveType")
    @ApiOperation(value = "保存分类")
    public Result saveType(@RequestBody Map<String, Type> para){
        Type type = para.get("type");
        //id为空 为添加分类， 否则为更新分类
        if (type.getId() == null){
            typeService.save(type);
        }else {
            typeService.updateById(type);
        }
        return new Result<>(true, StatusCode.OK, "保存成功", null);
    }

    @GetMapping("delete/{id}")
    @ApiOperation(value = "删除分类")
    public Result delete(@PathVariable String id){
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("type_id", id);
        List<Blog> blogs = blogService.list(wrapper);
        for (Blog blog : blogs) {
            blog.setTypeId("0");
            blogService.updateById(blog);
        }
        boolean remove = typeService.removeById(id);
        if (remove) {
            return new Result<>(true, StatusCode.OK, "删除成功", null);
        }else {
            return new Result<>(false, StatusCode.ERROR, "删除失败", null);
        }
    }
}

