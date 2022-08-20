package com.gh.myblog_backend.service.impl;

import com.gh.myblog_backend.entity.Message;
import com.gh.myblog_backend.mapper.MessageMapper;
import com.gh.myblog_backend.service.MessageService;
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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
