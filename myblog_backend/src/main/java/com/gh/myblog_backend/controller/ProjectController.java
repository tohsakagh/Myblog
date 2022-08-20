package com.gh.myblog_backend.controller;


import com.gh.myblog_backend.entity.Project;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@RequestMapping("/front/project")
@CrossOrigin
@Api(description = "前台项目")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("getProjectList")
    @ApiOperation(value = "获取项目列表")
    public Result getProjectList(){
        List<Project> list = projectService.list(null);
        return new Result<>(true, StatusCode.OK, "获取列表成功", list);
    }
}

