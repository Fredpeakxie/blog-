package com.fred.bean;

/**
 *
 * 用于记录 用户 喜欢或标记的 文章 之巅的对应关系
 * @auther fred
 * @create 2020-06-10 7:30
 */
public class UserArticleRelation {
    private Integer userId;
    private Integer blogId;

    public UserArticleRelation() {
    }

    public UserArticleRelation(Integer userId, Integer blogId) {
        this.userId = userId;
        this.blogId = blogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "UserArticleRelation{" +
                "userId=" + userId +
                ", blogId=" + blogId +
                '}';
    }
}
