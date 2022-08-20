package com.gh.myblog_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("t_project")
@ApiModel(value="Project对象")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名")
    private String title;

    @ApiModelProperty(value = "项目内容")
    private String content;

    @ApiModelProperty(value = "技术栈")
    private String techs;

    @ApiModelProperty(value = "项目图片")
    private String picUrl;

    @ApiModelProperty(value = "项目地址")
    private String url;

    @ApiModelProperty(value = "项目id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "项目类型")
    private Integer type;


}
