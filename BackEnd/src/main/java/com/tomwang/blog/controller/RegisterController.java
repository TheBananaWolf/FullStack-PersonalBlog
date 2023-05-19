package com.tomwang.blog.controller;

import com.tomwang.blog.service.LoginService;
import com.tomwang.blog.vo.Result;
import com.tomwang.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ljm
 * @Date 2021/10/13 10:43
 * @Version 1.0
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    //后端传递多个参数，前端只选用其需要的参数就可以了
    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        //sso 单点登陆，后期如果把登陆注册功能 提出起（单独的服务，可以独立提供接口）
        return loginService.register(loginParam);
    }
}
