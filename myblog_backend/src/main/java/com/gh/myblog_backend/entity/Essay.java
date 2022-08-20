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
@TableName("t_essay")
@ApiModel(value="Essay对象")
public class Essay implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "随笔id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "随笔标题")
    private String title;

    @ApiModelProperty(value = "随笔内容")
    private String content;

    @ApiModelProperty(value = "随笔图片")
    private String image;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "好评")
    private Long praise;

    @ApiModelProperty(value = "随笔颜色")
    private String color;


}
