package com.fred.bean;

/**
 * @auther fred
 * @create 2020-05-28 8:48
 */
public class Article {
    Integer blogId;
    Integer userId;
    String title;
    Integer likeNum;
    Integer readNum;
    Integer markNum;

    public Article() {
    }

    public Article(Integer blogId, Integer userId, String title, Integer likeNum, Integer readNum, Integer markNum) {
        this.blogId = blogId;
        this.userId = userId;
        this.title = title;
        this.likeNum = likeNum;
        this.readNum = readNum;
        this.markNum = markNum;
    }

    public Article(Integer userId, String title) {
        this.userId = userId;
        this.title = title;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getMarkNum() {
        return markNum;
    }

    public void setMarkNum(Integer markNum) {
        this.markNum = markNum;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Article{" +
                "blogId=" + blogId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", likeNum=" + likeNum +
                ", readNum=" + readNum +
                ", markNum=" + markNum +
                '}';
    }

}
