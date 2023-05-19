package com.tomwang.blog.controller;

import com.tomwang.blog.service.CategoryService;
import com.tomwang.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ljm
 * @Date 2021/10/16 22:09
 * @Version 1.0
 */
@RestController
//文章分类
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categories() {
        return categoryService.findAll();
    }

    @GetMapping("detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }


    @GetMapping("detail/{id}")
    public Result categoryDetailById(@PathVariable("id") Long id) {
        return categoryService.categoryDetailById(id);
    }

}
