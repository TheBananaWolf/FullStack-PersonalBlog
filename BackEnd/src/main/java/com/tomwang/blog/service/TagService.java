package com.tomwang.blog.service;

import com.tomwang.blog.vo.Result;
import com.tomwang.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 查询所有文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findADetailById(Long id);
}
