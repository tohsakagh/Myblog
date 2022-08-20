package com.gh.myblog_backend.service;

import com.gh.myblog_backend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
public interface UserService extends IService<User> {

    User checkUser(String nickname, String password);

    User updateUserLoginTime(User user);

    User saveAndRetureUer(User u);
}
