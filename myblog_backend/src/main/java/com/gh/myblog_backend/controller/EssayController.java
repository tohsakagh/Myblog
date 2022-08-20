package com.gh.myblog_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gh.myblog_backend.entity.Comment;
import com.gh.myblog_backend.entity.Essay;
import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.service.CommentService;
import com.gh.myblog_backend.service.EssayService;
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
@RequestMapping("/front/essay")
@CrossOrigin
@Api(description = "前台随笔")
public class EssayController {

    @Autowired
    private EssayService essayService;
    @Autowired
    private CommentService commentService;

    @GetMapping("getEssays")
    @ApiOperation(value = "获取随笔列表")
    public Result getEssays(){
        QueryWrapper<Essay> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Essay> list = essayService.list(wrapper);
        return new Result<>(true, StatusCode.OK, "获取随笔列表成功", list);
    }

    @GetMapping("getessayInfo/{id}")
    @ApiOperation(value = "获取随笔信息")
    public Result getessayInfo(@PathVariable String id){
        Essay essay = essayService.getById(id);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("essay_id", id);
        List<Comment> list = commentService.list(wrapper);
        for (Comment comment : list) {
            if (comment.getParentId() != null){
                QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", comment.getParentId());
                Comment one = commentService.getOne(queryWrapper);
                comment.setParentComment(one);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("essay", essay);
        map.put("comments", list);
        return new Result<>(true, StatusCode.OK, "获取随笔信息成功", map);
    }
}

