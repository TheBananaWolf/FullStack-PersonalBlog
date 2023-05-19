package com.tomwang.blog.vo.params;

import lombok.Data;

/**
 * @Author ljm
 * @Date 2021/10/12 20:06
 * @Version 1.0
 */
@Data
public class LoginParam {
    private String account;

    private String password;

    private String nickname;
}
