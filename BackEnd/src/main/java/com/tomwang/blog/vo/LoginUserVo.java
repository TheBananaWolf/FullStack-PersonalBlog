package com.tomwang.blog.vo;

import lombok.Data;

/**
 * @Author ljm
 * @Date 2021/10/12 22:31
 * @Version 1.0
 */
@Data
public class LoginUserVo {
    //与页面交互

    private String id;

    private String account;

    private String nickname;

    private String avatar;
}
