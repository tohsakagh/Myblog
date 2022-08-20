package com.gh.myblog_backend.controller.theAdminOfMyBlogGh;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.Development;
import com.gh.myblog_backend.entity.Essay;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.service.DevelopmentService;
import com.gh.myblog_backend.service.EssayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/theAdminOfMyBlogGh/essay")
@CrossOrigin
@Api(description = "随笔后台管理")
public class EssayAdminController {

    @Autowired
    private EssayService essayService;
    @Autowired
    private DevelopmentService developmentService;

    @PostMapping("addEssay")
    @ApiOperation(value = "添加随笔或修改随笔")
    public Result addEssay(@RequestBody Map<String, Essay> para) {
        Essay essay = para.get("essay");
        boolean save = false;
        String message = null;
        int code = 200;
        if (essay.getId() == null) {
            save = essayService.save(essay);
            QueryWrapper<Essay> wrapper = new QueryWrapper<>();
            wrapper.eq("title", essay.getTitle());
            Essay one = essayService.getOne(wrapper);
            Development development = new Development();
            development.setTask("发布了随笔: " + one.getTitle());
            development.setUrl("/essayInfo?id=" + one.getId());
            development.setColor("#2CF046");
            development.setType(3);
            development.setEssayId(one.getId());
            developmentService.save(development);
            message = save ? "发布随笔成功" : "发布随笔失败";
            code = save ? StatusCode.OK : StatusCode.ERROR;
        } else {
            save = essayService.updateById(essay);
            message = save ? "修改随笔成功" : "修改随笔失败";
            code = save ? StatusCode.OK : StatusCode.ERROR;
        }
        return new Result<>(save, code, message, null);
    }

    @GetMapping("delete/{id}")
    @ApiOperation(value = "删除随笔")
    private Result deleteEssay(@PathVariable String id) {
        boolean remove = essayService.removeById(id);
        QueryWrapper<Development> wrapper = new QueryWrapper<>();
        wrapper.eq("essay_id", id);
        boolean remove1 = developmentService.remove(wrapper);
        if (remove && remove1) {
            return new Result<>(true, StatusCode.OK, "删除随笔成功", null);
        } else {
            return new Result<>(false, StatusCode.ERROR, "删除随笔失败", null);
        }
    }
}

