package com.tomwang.blog.service;

import com.tomwang.blog.vo.CategoryVo;
import com.tomwang.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
