package com.gh.myblog_backend.service;

import com.gh.myblog_backend.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gh.myblog_backend.entity.po.TypeBlogs;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
public interface TypeService extends IService<Type> {

    List<TypeBlogs> listFullType();
}
