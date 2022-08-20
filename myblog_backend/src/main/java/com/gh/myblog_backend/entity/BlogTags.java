package com.gh.myblog_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
@TableName("t_blog_tags")
@ApiModel(value="BlogTags对象")
public class BlogTags implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.ID_WORKER_STR)
    private String blogsId;

    private String tagsId;


}
