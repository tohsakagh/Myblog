package com.gh.myblog_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gh.myblog_backend.entity.Tag;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<String> getBlogIdList(String id);
}
