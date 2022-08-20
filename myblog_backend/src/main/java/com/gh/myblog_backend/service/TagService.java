package com.gh.myblog_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gh.myblog_backend.entity.Tag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
public interface TagService extends IService<Tag> {


    List<String> getBlogIdList(String id);
}
