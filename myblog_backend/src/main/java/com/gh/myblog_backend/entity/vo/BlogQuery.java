package com.gh.myblog_backend.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * myblog_backend
 * 2022/8/15 20:56
 * Origin
 */
@SuppressWarnings("all")
@Data
@Api(description = "博客查询条件")
public class BlogQuery {

    @ApiModelProperty(value = "博客id")
    private String title;

    @ApiModelProperty(value = "类型id")
    private String typeId;
}
