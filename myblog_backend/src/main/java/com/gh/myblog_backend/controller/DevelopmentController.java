package com.gh.myblog_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.Development;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.service.DevelopmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-18
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/front/development")
@CrossOrigin
@Api(description = "归档")
public class DevelopmentController {

    @Autowired
    private DevelopmentService developmentService;

    @GetMapping("getList")
    @ApiOperation(value = "获取所有动态")
    public Result getList() {
        QueryWrapper<Development> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Development> list = developmentService.list(wrapper);
        return new Result<>(true, StatusCode.OK, "获取动态成功", list);
    }
}

