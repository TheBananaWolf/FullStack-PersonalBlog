package com.tomwang.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author ljm
 * @Date 2021/10/10 20:09
 * @Version 1.0
 */
@SpringBootApplication
public class BolgApp {
    public static void main(String[] args) {
        SpringApplication.run(BolgApp.class,args);
    }
}
