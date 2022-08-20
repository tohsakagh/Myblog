package com.gh.myblog_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gh.myblog_backend.entity.BlogTags;
import com.gh.myblog_backend.entity.Tag;
import com.gh.myblog_backend.mapper.TagMapper;
import com.gh.myblog_backend.service.BlogTagsService;
import com.gh.myblog_backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private BlogTagsService blogTagsService;


    @Override
    public List<String> getBlogIdList(String id) {
        QueryWrapper<BlogTags> wrapper = new QueryWrapper<>();
        wrapper.eq("tags_id", id);
        List<BlogTags> list = blogTagsService.list(wrapper);
        List<String> list1 = new ArrayList<>();
        for (BlogTags blogTags : list) {
            list1.add(blogTags.getBlogsId());
        }
        return list1;
    }
}
