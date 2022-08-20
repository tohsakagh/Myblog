package com.gh.myblog_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.Blog;
import com.gh.myblog_backend.entity.Type;
import com.gh.myblog_backend.entity.po.TypeBlogs;
import com.gh.myblog_backend.mapper.TypeMapper;
import com.gh.myblog_backend.service.BlogService;
import com.gh.myblog_backend.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
@SuppressWarnings("all")
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private BlogService blogService;

    @Override
    public List<TypeBlogs> listFullType() {
        List<Type> types = baseMapper.selectList(null);
        List<TypeBlogs> typeList = new ArrayList<>();
        for (Type type : types) {
            TypeBlogs typeBlogs = new TypeBlogs();
            BeanUtils.copyProperties(type, typeBlogs);
            QueryWrapper<Blog> wrapper = new QueryWrapper<>();
            wrapper.eq("type_id", type.getId());
            List<Blog> blogList= blogService.list(wrapper);
            typeBlogs.setBlogs(blogList);
            typeList.add(typeBlogs);
        }
        return typeList;
    }
}
