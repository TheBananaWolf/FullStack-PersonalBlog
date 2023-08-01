package com.tomwang.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tomwang.blog.dao.dos.Archives;
import com.tomwang.blog.dao.pojo.Article;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();

    //Page对象 是mybatisplus的page对象 代表要用到mybatisplus的一个分页
    //IPage也是mybatisplus的，代表我们自定义的分页查询
    IPage<Article> listArticle(Page<Article> page,
                               Long categoryId,
                               Long tagId,
                               String year,
                               String month);
}
