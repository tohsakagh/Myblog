package com.gh.myblog_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.User;
import com.gh.myblog_backend.mapper.UserMapper;
import com.gh.myblog_backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User checkUser(String nickname, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", nickname).eq("password", password);
        User user = baseMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User updateUserLoginTime(User user) {
        baseMapper.updateById(user);
        return user;
    }

    @Override
    public User saveAndRetureUer(User u) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", u.getNickname());
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            return null;
        }
        int random = new Random().nextInt(5) + 1;
        u.setType("1");
        u.setAvatar("https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/user/default" + random + ".jpg");
        baseMapper.insert(u);
        User user = baseMapper.selectOne(wrapper);
        return user;
    }


}
