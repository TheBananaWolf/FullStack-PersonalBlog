package com.tomwang.blog.utils;

import com.tomwang.blog.dao.pojo.SysUser;

/**
 * @Author ljm
 * @Date 2021/10/14 15:50
 * @Version 1.0
 */
public class UserThreadLocal {

    private UserThreadLocal() {
    }

    private static final ThreadLocal<SysUser> LOCAL =new ThreadLocal<SysUser>();
    //放入
    public static void put(SysUser user){
        LOCAL.set(user);
    }

    //取出
    public static SysUser get(){
        return LOCAL.get();
    }
    //删除
    public static void remove(){
        LOCAL.remove();
    }




}
