package com.tomwang.blog.service;

import com.tomwang.blog.vo.Result;
import com.tomwang.blog.vo.params.ArticleParam;
import com.tomwang.blog.vo.params.PageParams;

/**
 * @Author ljm
 * @Date 2021/10/11 10:30
 * @Version 1.0
 */
public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 文章发布服务
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
