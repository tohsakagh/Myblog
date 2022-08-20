package com.gh.myblog_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.Blog;
import com.gh.myblog_backend.entity.BlogTags;
import com.gh.myblog_backend.entity.Tag;
import com.gh.myblog_backend.entity.Type;
import com.gh.myblog_backend.entity.po.FullBlog;
import com.gh.myblog_backend.mapper.BlogMapper;
import com.gh.myblog_backend.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gh.myblog_backend.service.BlogTagsService;
import com.gh.myblog_backend.service.TagService;
import com.gh.myblog_backend.service.TypeService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogTagsService blogTagsService;
    @Autowired
    private TagService tagService;

    @Override
    public int countAllView() {
      return  baseMapper.countAllView();
    }

    @Override
    public int countAppreciate() {
        return baseMapper.countAppreciate();
    }

    @Override
    public List<FullBlog> getRecommendBlogs() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time").last("limit 5");
        List<Blog> list = baseMapper.selectList(wrapper);
        List<FullBlog> blogTypes = new ArrayList<>();
        for (Blog blog : list) {
            FullBlog blogType = new FullBlog();
            BeanUtils.copyProperties(blog, blogType);
            QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
            typeQueryWrapper.eq("id", blog.getTypeId()).select("name");
            Type one = typeService.getOne(typeQueryWrapper);
            blogType.setTypeName(one.getName());
            blogTypes.add(blogType);
        }
        return blogTypes;
    }

    @Override
    public void saveFullBlog(FullBlog fullBlog) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(fullBlog, blog);
        String tagIds = fullBlog.getTagIds();
        baseMapper.insert(blog);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "type_id").orderByDesc("create_time").last("limit 1");
        blog= baseMapper.selectOne(wrapper);

        //该博客所对应分类数量加1
        QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.eq("id", blog.getTypeId());
        Type type = typeService.getOne(typeQueryWrapper);
        type.setBlogCount(type.getBlogCount() + 1);
        typeService.updateById(type);
        if (tagIds != null) {
            for (String tagId : tagIds.split(",")) {
                BlogTags blogTags = new BlogTags();
                blogTags.setTagsId(tagId);
                blogTags.setBlogsId(blog.getId());
                blogTagsService.save(blogTags);

                //博客所对标签数量加1
                QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
                tagQueryWrapper.eq("id", tagId);
                Tag tag = tagService.getOne(tagQueryWrapper);
                tag.setBlogCount(tag.getBlogCount() + 1);
                tagService.updateById(tag);
            }
        }
    }

    @Override
    public boolean removeBlogById(String id) {
        QueryWrapper<Blog> wrapper = new QueryWrapper();
        wrapper.eq("id", id).select("type_id");
        Blog blog = baseMapper.selectOne(wrapper);

        //该博客所属的分类博客数量减1
        QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.eq("id", blog.getTypeId());
        Type type = typeService.getOne(typeQueryWrapper);
        type.setBlogCount(type.getBlogCount() - 1);
        typeService.updateById(type);

        //该博客所属的标签博客数量减一
        List<String> tagIds = new ArrayList<>();
        QueryWrapper<BlogTags> blogTagsQueryWrapper = new QueryWrapper<>();
        blogTagsQueryWrapper.eq("blogs_id", id);
        List<BlogTags> list = blogTagsService.list(blogTagsQueryWrapper);
        if (list.size() > 0){
            for (BlogTags blogTags : list) {
                QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
                tagQueryWrapper.eq("id", blogTags.getTagsId());
                Tag tag = tagService.getOne(tagQueryWrapper);
                tag.setBlogCount(tag.getBlogCount() - 1);
                tagService.updateById(tag);
                blogTagsService.removeById(blogTags.getBlogsId());
            }
        }
        int delete = baseMapper.deleteById(id);
        return delete > 0;
    }
}
