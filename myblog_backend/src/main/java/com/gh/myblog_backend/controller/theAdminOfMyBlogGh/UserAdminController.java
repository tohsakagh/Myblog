package com.gh.myblog_backend.controller.theAdminOfMyBlogGh;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gh.myblog_backend.entity.*;
import com.gh.myblog_backend.service.CommentService;
import com.gh.myblog_backend.service.MessageService;
import com.gh.myblog_backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-15
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/theAdminOfMyBlogGh/user")
@CrossOrigin
@Api(description = "用户管理")
public class UserAdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MessageService messageService;

    @GetMapping("getUseList/{current}/{limit}")
    @ApiOperation(value = "获取用户列表")
    public Result getUseList(@PathVariable long current,
                             @PathVariable long limit){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("create_time");
        Page<User> userPage = new Page<>(current, limit);
        userService.page(userPage, wrapper);
        long total = userPage.getTotal();
        List<User> records = userPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        return new Result<>(true, StatusCode.OK, "获取成功", map);
    }

    @GetMapping("delete/{id}")
    @ApiOperation(value = "删除用户")
    public Result delete(@PathVariable String id){
        int random = new Random().nextInt(5) + 1;
        QueryWrapper<Comment> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id", id);
        Comment comment = commentService.getOne(wrapper1);
        if (comment != null) {
            comment.setNickname("已注销用户");
            comment.setAvatar("https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/user/default" + random + ".jpg");
            commentService.updateById(comment);
        }

        QueryWrapper<Message> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("user_id", id);
        Message message = messageService.getOne(wrapper2);
        if (message != null){
            message.setNickname("已注销用户");
            message.setAvatar("https://myblog-1313287673.cos.ap-chongqing.myqcloud.com/myblog/user/default\" + random + \".jpg");
            messageService.updateById(message);
        }

        boolean remove = userService.removeById(id);
        if (remove){
            return new Result<>(true, StatusCode.OK, "删除用户成功", null);
        }else {
            return new Result<>(false, StatusCode.ERROR, "删除用户失败", null);
        }
    }


}

