package com.gh.myblog_backend.controller;


import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.entity.User;
import com.gh.myblog_backend.service.UserService;
import com.gh.myblog_backend.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/front/user")
@CrossOrigin
@Slf4j
@Api(description = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result login(@RequestBody Map<String, User> para) {
        User u = para.get("user");
        User user = userService.checkUser(u.getNickname(), u.getPassword());
        if (user != null) {
            String token = TokenUtil.sign(user);
            Map<String, Object> info = new HashMap<>();
            User newUser = userService.updateUserLoginTime(user);
            info.put("user", newUser);
            info.put("token", token);
            if (newUser.getType() == "0"){
                return new Result(true, StatusCode.OK, "站主登录成功", info);
            }
            return new Result(true, StatusCode.OK, "登录成功", info);
        } else {
            return new Result(false, StatusCode.ERROR, "用户名或密码错误", null);
        }
    }

    @PostMapping(value = "/registor")
    @ApiOperation(value = "注册")
    public Result post(@RequestBody Map<String, User> para)  {
        User u = para.get("user");
        if (u != null) {
            User user = userService.saveAndRetureUer(u);
            if (user == null){
                return new Result(false, StatusCode.ERROR, "用户名重复", null);
            }
            log.info(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + ":  添加用户: " + user.getNickname());
            String token = TokenUtil.sign(user);
            Map<String, Object> info = new HashMap<>();
            info.put("user", user);
            info.put("token", token);
            return new Result(true, StatusCode.OK, "注册成功", info);
        } else {
            return new Result(false, StatusCode.ERROR, "注册失败", null);
        }
    }

    @GetMapping(value = "/getUsers")
    @ApiOperation(value = "获取用户列表")
    public Result get(){
        log.info("获取用户列表: " + userService.list(null));
        return new Result(true, StatusCode.OK, "获取用户列表成功", userService.list(null));
    }
}

