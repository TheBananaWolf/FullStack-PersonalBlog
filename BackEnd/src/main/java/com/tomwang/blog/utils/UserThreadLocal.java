package com.tomwang.blog.utils;

import com.tomwang.blog.dao.pojo.SysUser;

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
