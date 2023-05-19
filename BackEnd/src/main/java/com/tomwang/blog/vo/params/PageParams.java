package com.tomwang.blog.vo.params;

import lombok.Data;

/**
 * @Author ljm
 * @Date 2021/10/10 22:13
 * @Version 1.0
 * vo里面放置了前端想要的数据
 */
@Data
public class PageParams {

    private int page =1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    //传递6的话变成06
    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }

}
