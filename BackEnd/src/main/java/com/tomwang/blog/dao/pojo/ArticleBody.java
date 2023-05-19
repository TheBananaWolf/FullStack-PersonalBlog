package com.tomwang.blog.dao.pojo;

import lombok.Data;

/**
 * @Author ljm
 * @Date 2021/10/14 22:07
 * @Version 1.0
 * 内容表
 */
@Data
public class ArticleBody {

        private Long id;
        private String content;
        private String contentHtml;
        private Long articleId;

}
