package com.example.bean;

public class Article {
    private int userId;
    private int blogId;
    private String title;
    private int readNum;
    private int likeNum;
    private int markNum;

    public Article() {
    }

    public Article(int userId, int blogId, String title, int readNum, int likeNum, int markNum) {
        this.userId = userId;
        this.blogId = blogId;
        this.title = title;
        this.readNum = readNum;
        this.likeNum = likeNum;
        this.markNum = markNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getMarkNum() {
        return markNum;
    }

    public void setMarkNum(int markNum) {
        this.markNum = markNum;
    }

    @Override
    public String toString() {
        return "Article{" +
                "userId=" + userId +
                ", blogId=" + blogId +
                ", title='" + title + '\'' +
                ", readNum=" + readNum +
                ", likeNum=" + likeNum +
                ", markNum=" + markNum +
                '}';
    }
}
