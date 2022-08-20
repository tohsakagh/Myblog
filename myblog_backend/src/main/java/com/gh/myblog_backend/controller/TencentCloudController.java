package com.gh.myblog_backend.controller;

import com.gh.myblog_backend.entity.Result;
import com.gh.myblog_backend.entity.StatusCode;
import com.gh.myblog_backend.utils.TencentCloudUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * myblog_backend
 * 2022/8/15 13:36
 * Origin
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/tencentcloud")
@CrossOrigin
@Api(description = "与腾讯云交互")
@Slf4j
public class TencentCloudController {

    @PostMapping("/upload/essay")
    @ApiOperation(value = "上传随笔内容图像")
    public Result uploadEssay(@RequestParam("file") MultipartFile file){
        String url = TencentCloudUtil.upload(file, "essay");
        if (url == null || url == "error"){
            return new Result(false, StatusCode.ERROR, "上传图片失败", null);
        }
        return new Result(true, StatusCode.OK, "上传图片成功", url);
    }

    @PostMapping("/upload/essay/info")
    @ApiOperation(value = "上传随笔内容图像")
    public Result uploadEssayInfo(@RequestParam("file") MultipartFile file){
        String url = TencentCloudUtil.upload(file, "essay/info");
        if (url == null || url == "error"){
            return new Result(false, StatusCode.ERROR, "上传图片失败", null);
        }
        return new Result(true, StatusCode.OK, "上传图片成功", url);
    }

    @PostMapping("/upload/user")
    @ApiOperation(value = "上传用户头像")
    public Result uploadUser(@RequestParam("file") MultipartFile file){
        String url = TencentCloudUtil.upload(file, "user");
        if (url == null || url == "error"){
            return new Result(false, StatusCode.ERROR, "上传图片失败", null);
        }
        return new Result(true, StatusCode.OK, "上传图片成功", url);
    }

    @PostMapping("/upload/blog")
    @ApiOperation(value = "上传博客封面")
    public Result uploadBlog(@RequestParam("file") MultipartFile file){
        String url = TencentCloudUtil.upload(file, "blog");
        if (url == null || url == "error"){
            return new Result(false, StatusCode.ERROR, "上传图片失败", null);
        }
        return new Result(true, StatusCode.OK, "上传图片成功", url);
    }

    @PostMapping("/upload/blog/info")
    @ApiOperation(value = "上传博客内容图片")
    public Result uploadBlogInfo(@RequestParam("file") MultipartFile file){
        String url = TencentCloudUtil.upload(file, "blog/info");
        if (url == null || url == "error"){
            return new Result(false, StatusCode.ERROR, "上传图片失败", null);
        }
        return new Result(true, StatusCode.OK, "上传图片成功", url);
    }

    @PostMapping("/upload/type")
    @ApiOperation(value = "上传分类封面")
    public Result uploadType(@RequestParam("file") MultipartFile file){
        String url = TencentCloudUtil.upload(file, "type");
        if (url == null || url == "error"){
            return new Result(false, StatusCode.ERROR, "上传图片失败", null);
        }
        return new Result(true, StatusCode.OK, "上传图片成功", url);
    }

    @PostMapping("/upload/project")
    @ApiOperation(value = "上传分类封面")
    public Result uploadProject(@RequestParam("file") MultipartFile file){
        String url = TencentCloudUtil.upload(file, "project");
        if (url == null || url == "error"){
            return new Result(false, StatusCode.ERROR, "上传图片失败", null);
        }
        return new Result(true, StatusCode.OK, "上传图片成功", url);
    }


    @GetMapping("delete/essay/{filename}")
    @ApiOperation(value = "删除essay上的图片")
    public Result deleteEssay(@PathVariable String filename){
        TencentCloudUtil.delete(filename, "essay");
        return new Result(true, StatusCode.OK, "删除图片成功", null);
    }

    @GetMapping("delete/blog/{filename}")
    @ApiOperation(value = "删除essay上的图片")
    public Result deleteBlog(@PathVariable String filename){
        TencentCloudUtil.delete(filename, "blog");
        return new Result(true, StatusCode.OK, "删除图片成功", null);
    }
}
