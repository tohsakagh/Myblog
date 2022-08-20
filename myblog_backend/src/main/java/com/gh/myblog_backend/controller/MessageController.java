package com.gh.myblog_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gh.myblog_backend.entity.Message;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/front/message")
@CrossOrigin
@Api("前台留言")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("addMessage")
    @ApiOperation(value = "添加留言")
    public Result addMessage(@RequestBody Map<String, Message> para){
        Message message = para.get("message");
        message.setContent(message.getContent().trim());
        boolean save = messageService.save(message);
        if (save){
            return new Result<>(true, StatusCode.OK, "留言成功", null);
        }else {
            return new Result<>(false, StatusCode.ERROR, "留言失败", null);
        }
    }

    @GetMapping("getMessages/{current}/{limit}")
    @ApiOperation(value = "获取留言")
    public Result getMessages(@PathVariable long current,
                              @PathVariable long limit){
        Page<Message> messagePage = new Page<>(current, limit);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        messageService.page(messagePage, wrapper);
        List<Message> records = messagePage.getRecords();
        long total = messagePage.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        return new Result<>(true, StatusCode.OK, "获取留言成功", map);
    }

    @GetMapping("delete/{id}")
    public Result delete(@PathVariable String id){
        boolean remove = messageService.removeById(id);
        if (remove){
            return new Result<>(true, StatusCode.OK, "删除成功", null);
        }else {
            return new Result<>(false, StatusCode.OK, "删除失败", null);
        }
    }

}

