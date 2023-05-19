package com.tomwang.blog.controller;

import com.tomwang.blog.dao.pojo.SysUser;
//import com.mszlu.blog.utils.UserThreadLocal;
import com.tomwang.blog.utils.UserThreadLocal;
import com.tomwang.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
