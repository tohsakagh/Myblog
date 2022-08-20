package com.gh.myblog_backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_comment")
@ApiModel(value="Comment对象")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "评论id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "评论时间")
    private Date createTime;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "博客id'")
    private String blogId;

    @ApiModelProperty(value = "博客内容")
    private String content;

    @ApiModelProperty(value = "回复内容id")
    private String parentId;

    @ApiModelProperty(value = "0为管理员的评论")
    private Integer adminComment;

    @ApiModelProperty(value = "随笔id")
    private String essayId;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "回复的评论")
    @TableField(exist = false)
    private Comment parentComment;


}
