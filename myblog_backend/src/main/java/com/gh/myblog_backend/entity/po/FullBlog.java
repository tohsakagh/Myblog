package com.gh.myblog_backend.entity.po;

import com.gh.myblog_backend.entity.Comment;
import com.gh.myblog_backend.entity.Tag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * myblog_backend
 * 2022/8/15 19:42
 * Origin
 */
@SuppressWarnings("all")
@ToString
@Data
@Api(value = "包含博客所有具体信息")
public class FullBlog {

    @ApiModelProperty(value = "博客id")
    private String id;

    @ApiModelProperty(value = "好评")
    private Long appreciation;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "0原创,1转载")
    private String flag;

    @ApiModelProperty(value = "是否发布,0未发布,1发布")
    private String  published;

    @ApiModelProperty(value = "博客标题")
    private String title;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "观看人数")
    private Integer views;

    @ApiModelProperty(value = "类型id")
    private String typeId;

    @ApiModelProperty(value = "类型名字")
    private String typeName;

    @ApiModelProperty(value = "标签id列表")
    private String tagIds;

    @ApiModelProperty(value = "标签列表")
    private List<Tag> tags;

    @ApiModelProperty(value = "所有评论")
    private List<Comment> comments;
}
