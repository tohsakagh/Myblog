package com.gh.myblog_backend.controller.theAdminOfMyBlogGh;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.*;
import com.gh.myblog_backend.service.BlogTagsService;
import com.gh.myblog_backend.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/theAdminOfMyBlogGh/tag")
@CrossOrigin
@Api(description = "标签后台管理")
public class TagAdminController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagsService blogTagsService;

    @GetMapping("getFullTagList")
    @ApiOperation(value = "标签列表")
    public Result getFullTagList() {
        List<Tag> list = tagService.list(null);
        return new Result(true, StatusCode.OK, "获取所有博客标签成功", list);
    }

    @PostMapping("/saveTag")
    @ApiOperation(value = "保存标签")
    public Result createTag(@RequestBody Map<String, Tag> param) {
        Tag tag = param.get("tag");
        if (tag.getId() == null) {
            tagService.save(tag);
        }else {
            tagService.updateById(tag);
        }
        return new Result(true, StatusCode.OK, "添加标签成功", null);
    }

    @GetMapping("/dealDeletedTag/{id}")
    @ApiOperation(value = "删除博客中的标签")
    public Result dealDeletedTag(@PathVariable String id) {
        QueryWrapper<BlogTags> wrapper = new QueryWrapper<>();
        wrapper.eq("tags_id", id);
        boolean remove = blogTagsService.remove(wrapper);
        if (remove) {
            return new Result<>(true, StatusCode.OK, "删除成功", null);
        } else {
            return new Result<>(true, StatusCode.ERROR, "删除失败", null);
        }
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除标签")
    public Result deletedTag(@PathVariable String id) {
        QueryWrapper<BlogTags> wrapper = new QueryWrapper<>();
        wrapper.eq("tags_id", id);
        boolean remove1 = blogTagsService.remove(wrapper);
        boolean remove2 = tagService.removeById(id);
        if (remove1 && remove2) {
            return new Result<>(true, StatusCode.OK, "删除成功", null);
        } else {
            return new Result<>(true, StatusCode.ERROR, "删除失败", null);
        }
    }


}

