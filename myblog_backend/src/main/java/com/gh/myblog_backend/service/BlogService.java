package com.gh.myblog_backend.service;

import com.gh.myblog_backend.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gh.myblog_backend.entity.po.FullBlog;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
public interface BlogService extends IService<Blog> {

    int countAllView();

    int countAppreciate();

    List<FullBlog> getRecommendBlogs();

    void saveFullBlog(FullBlog fullBlog);

    boolean removeBlogById(String id);
}
