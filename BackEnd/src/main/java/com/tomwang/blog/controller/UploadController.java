package com.tomwang.blog.controller;

import com.tomwang.blog.utils.QiniuUtils;
import com.tomwang.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @Author ljm
 * @Date 2021/10/19 15:27
 * @Version 1.0
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    //https://blog.csdn.net/justry_deng/article/details/80855235 MultipartFile介绍
    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){
        //原始文件名称 比如说aa.png
        String originalFilename = file.getOriginalFilename();
        //唯一的文件名称
        String fileName =  UUID.randomUUID().toString()+"."+StringUtils.substringAfterLast(originalFilename, ".");
        //上传文件上传到那里呢？　七牛云　云服务器
        //降低我们自身应用服务器的带宽消耗
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url+fileName);
        }
        return Result.fail(20001,"上传失败");



    }
}
