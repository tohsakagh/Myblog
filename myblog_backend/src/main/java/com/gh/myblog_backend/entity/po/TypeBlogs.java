package com.gh.myblog_backend.entity.po;

import com.gh.myblog_backend.entity.Blog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * myblog_backend
 * 2022/8/15 18:58
 * Origin
 */
@SuppressWarnings("all")
@Data
@ApiModel(value="Type对象与Blog对象", description="")
public class TypeBlogs {

    @ApiModelProperty(value = "类型id")
    private String id;

    @ApiModelProperty(value = "类型名字")
    private String name;

    @ApiModelProperty(value = "类型代表颜色")
    private String color;

    @ApiModelProperty(value = "类型图片")
    private String picUrl;

    @ApiModelProperty(value = "类型包含博客")
    private List<Blog> blogs = new ArrayList<>();
}
