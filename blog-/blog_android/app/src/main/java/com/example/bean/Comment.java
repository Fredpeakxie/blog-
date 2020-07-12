package com.example.bean;

/**
 * @auther fred
 * @create 2020-06-12 8:36
 */
public class Comment {
    Integer blogId;
    Integer userId;
    String comment;

    String userNickName;


    public Comment() {
    }

    public Comment(Integer blogId, Integer userId, String comment) {
        this.blogId = blogId;
        this.userId = userId;
        this.comment = comment;
    }

    public Comment(Integer blogId, Integer userId, String comment, String userNickName) {
        this.blogId = blogId;
        this.userId = userId;
        this.comment = comment;
        this.userNickName = userNickName;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "blogId=" + blogId +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
