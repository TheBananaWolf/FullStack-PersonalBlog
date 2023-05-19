package com.tomwang.blog.controller;

import com.tomwang.blog.common.aop.LogAnnotation;
import com.tomwang.blog.common.cache.Cache;
import com.tomwang.blog.service.ArticleService;
import com.tomwang.blog.vo.Result;
import com.tomwang.blog.vo.params.ArticleParam;
import com.tomwang.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ljm
 * @Date 2021/10/10 21:56
 * @Version 1.0
 */
//json数据进行交互
@RestController
@RequestMapping("articles")
public class ArticleController {


    @Autowired
    private ArticleService articleService;


    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    //加上此注解，代表要对此接口记录日志
    @LogAnnotation(module = "文章",operation = "获取文章列表")
//    @Cache(expire = 5*60*1000,name = "list_article")
    public Result listArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);

    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5*60*1000,name = "hot_article")
    public Result hotArticle(){
        int limit=5;
        return articleService.hotArticle(limit);

    }

    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5*60*1000,name = "new_article")
    public Result newArticles(){
        int limit=5;
        return articleService.newArticles(limit);

    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();

    }

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    //  @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
    //  而最常用的使用请求体传参的无疑是POST请求了，所以使用@RequestBody接收数据时，一般都用POST方式进行提交。
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){

        return articleService.publish(articleParam);
    }


}
