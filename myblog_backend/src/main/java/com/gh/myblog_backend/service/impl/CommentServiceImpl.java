package com.gh.myblog_backend.service.impl;

import com.gh.myblog_backend.entity.Comment;
import com.gh.myblog_backend.mapper.CommentMapper;
import com.gh.myblog_backend.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
