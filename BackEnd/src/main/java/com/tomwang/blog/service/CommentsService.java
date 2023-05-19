package com.tomwang.blog.service;

import com.tomwang.blog.vo.Result;
import com.tomwang.blog.vo.params.CommentParam;

/**
 * @Author ljm
 * @Date 2021/10/16 10:11
 * @Version 1.0
 */
public interface CommentsService {
    /**
     * 根据文章id查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
