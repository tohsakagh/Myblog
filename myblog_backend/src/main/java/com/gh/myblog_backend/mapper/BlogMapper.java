package com.gh.myblog_backend.mapper;

import com.gh.myblog_backend.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
public interface BlogMapper extends BaseMapper<Blog> {

    int countAllView();

    int countAppreciate();
}
