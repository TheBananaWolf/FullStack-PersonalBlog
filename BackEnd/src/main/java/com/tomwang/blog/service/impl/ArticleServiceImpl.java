package com.tomwang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tomwang.blog.dao.dos.Archives;
import com.tomwang.blog.dao.mapper.ArticleMapper;
import com.tomwang.blog.dao.mapper.ArticleTagMapper;
import com.tomwang.blog.dao.pojo.Article;
import com.tomwang.blog.dao.mapper.ArticleBodyMapper;
import com.tomwang.blog.dao.pojo.ArticleBody;
import com.tomwang.blog.dao.pojo.ArticleTag;
import com.tomwang.blog.dao.pojo.SysUser;
import com.tomwang.blog.service.*;
import com.tomwang.blog.service.*;
import com.tomwang.blog.utils.UserThreadLocal;
import com.tomwang.blog.vo.ArticleBodyVo;
import com.tomwang.blog.vo.ArticleVo;
import com.tomwang.blog.vo.Result;
import com.tomwang.blog.vo.TagVo;
import com.tomwang.blog.vo.params.ArticleParam;
import com.tomwang.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author ljm
 * @Date 2021/10/11 10:32
 * @Version 1.0
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleTagMapper articleTagMapper;




//    @Override
//    public Result listArticle(PageParams pageParams) {
//        /**
//         * 1、分页查询article数据库表
//         */
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if (pageParams.getCategoryId()!=null) {
//            //and category_id=#{categoryId}
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if(pageParams.getTagId()!=null){
//            //加入标签条件查询
//            //article表中并没有tag字段 一篇文章有多个标签
//            //articie_tog article_id 1：n tag_id
//            //我们需要利用一个全新的属于文章标签的queryWrapper将这篇文章的article_Tag查出来，保存到一个list当中。
//            // 然后再根据queryWrapper的in方法选择我们需要的标签即可。
//
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleTags.size() > 0) {
//                // and id in(1,2,3)
//                queryWrapper.in(Article::getId,articleIdList);
//            }
//
//        }
//        //是否置顶进行排序,        //时间倒序进行排列相当于order by create_data desc
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        //分页查询用法 https://blog.csdn.net/weixin_41010294/article/details/105726879
//        List<Article> records = articlePage.getRecords();
//        // 要返回我们定义的vo数据，就是对应的前端数据，不应该只返回现在的数据需要进一步进行处理
//        List<ArticleVo> articleVoList =copyList(records,true,true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        //"limit"字待串后要加空格，不要忘记加空格，不然会把数据拼到一起
        queryWrapper.last("limit "+limit);
        //select id,title from article order by view_counts desc limt 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        //返回vo对象
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        //"limit"字待串后要加空格，不要忘记加空格，不然会把数据拼到一起
        queryWrapper.last("limit "+limit);
        //select id,title from article order by create_data desc limt 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        //返回vo对象
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Autowired
    private ThreadService threadService;
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1、根据id查询文章信息
         * 2、根据bodyId和categoryid去做关联查询
         */
        //查出的表是这个
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true, true);

        //线程池
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1、发布文章 目的构建Article对象
         * 2、作者id,当前的登陆用户
         * 3、标签 要将标签加入到关联列表当中
         * 4、body 内容存储 article bodyId
         */
        //此接口要加入到登陆拦截当中
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.valueOf(articleParam.getCategory().getId()));
        //插入之后会生成一个文章id
        //官网解释:insert后主键会自动set到实体的ID字段，所以你只需要getID()就好了
        //利用主键自增，mp的insert操作后id值会写回到参数对象中，mybatisplus的回写操作
        this.articleMapper.insert(article);
        //tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);

            }

        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        //插入完之后再给一个id
        article.setBodyId(articleBody.getId());
        //MybatisPlus中的save方法什么时候执行insert，什么时候执行update
        // https://www.cxyzjd.com/article/Horse7/103868144
        articleMapper.updateById(article);

        HashMap<String, String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;


    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article,articleVo);
        //时间没法copy因为是long型
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口 都需要标签 ，作者信息
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

}
