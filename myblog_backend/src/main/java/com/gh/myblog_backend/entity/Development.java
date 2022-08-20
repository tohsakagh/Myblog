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
 * @since 2022-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_development")
@ApiModel(value="Development对象")
public class Development implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "动态id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "动态内容")
    private String task;

    @ApiModelProperty(value = "0为网站动态,1位项目动态,2为博客动态，3位随笔动态")
    private Integer type;

    @ApiModelProperty(value = "文字颜色")
    private String color;

    @ApiModelProperty(value = "文章地址")
    private String url;

    @ApiModelProperty(value = "博客id")
    private String blogId;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "随笔id")
    private String essayId;
}
