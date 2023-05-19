package com.tomwang.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomwang.blog.dao.pojo.Tag;

import java.util.List;

/**
 * @Author ljm
 * @Date 2021/10/10 21:52
 * @Version 1.0
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热的标签前n条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
