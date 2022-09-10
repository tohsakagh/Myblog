package com.gh.myblog_backend.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gh.myblog_backend.entity.Development;
import com.gh.myblog_backend.entity.Project;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.service.DevelopmentService;
import com.gh.myblog_backend.service.ProjectService;
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
@RequestMapping("/theAdminOfMyBlogGh/project")
@CrossOrigin
@Api(description = "项目管理")
public class ProjectAdminController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private DevelopmentService developmentService;

    @PostMapping("saveProject")
    @ApiOperation(value = "保存项目")
    public Result saveProject(@RequestBody Map<String, Project> para){
        Project project = para.get("project");
        boolean save = false;
        String message = null;
        int code = 200;
        //如果project有id为更新，否则x新增
        if (project.getId() == null){
            save = projectService.save(project);
            QueryWrapper<Project> wrapper = new QueryWrapper<>();
            wrapper.eq("title", project.getTitle());
            Project one = projectService.getOne(wrapper);
            Development development = new Development();
            development.setTask("发布了项目: " + one.getTitle());
            development.setUrl("/projectInfo?id=" + one.getId());
            development.setColor("#F9EA15");
            development.setType(1);
            development.setProjectId(one.getId());
            developmentService.save(development);
            message = save ? "发布项目成功" : "发布项目失败";
            code = save ? StatusCode.OK : StatusCode.ERROR;
        }else {
            save = projectService.updateById(project);
            message = save ? "修改项目成功" : "修改项目失败";
            code = save ? StatusCode.OK : StatusCode.ERROR;
        }
        return new Result<>(save, code, message, null);
    }

    @GetMapping("getProjectList/{current}/{limit}")
    @ApiOperation(value = "获取项目列表")
    private Result getProjectList(){
        Page<Project> projectPage = new Page<>();
        projectService.page(projectPage, null);
        List<Project> records = projectPage.getRecords();
        long total = projectPage.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        return new Result<>(true, StatusCode.OK, "获取项目列表成功", map);
    }

    @GetMapping("delete/{id}")
    @ApiOperation(value = "删除项目")
    public Result delete(@PathVariable String id){
        boolean remove = projectService.removeById(id);
        QueryWrapper<Development> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id", id);
        boolean remove1 = developmentService.remove(wrapper);
        if (remove && remove1){
            return new Result<>(true, StatusCode.OK, "删除项目成功", null);
        }else {
            return new Result<>(false, StatusCode.ERROR, "删除项目失败", null);
        }
    }

}

