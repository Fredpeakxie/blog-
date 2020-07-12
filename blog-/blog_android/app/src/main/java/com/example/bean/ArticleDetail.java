package com.example.bean;

import java.io.Serializable;

/**
 * @auther fred
 * @create 2020-05-29 11:13
 */
public class ArticleDetail implements Serializable {
    public static final long serialVersionUID = 1L;
    private Article article;
    private UserDetail userDetail;
    private String articleSimple;



    public ArticleDetail() {
    }

    public ArticleDetail(Article article, UserDetail userDetail) {
        this.article = article;
        this.userDetail = userDetail;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public String getArticleSimple() {
        return articleSimple;
    }

    public void setArticleSimple(String articleSimple) {
        this.articleSimple = articleSimple;
    }
    @Override
    public String toString() {
        return "ArticleDetail{" +
                "article=" + article +
                ", userDetail=" + userDetail +
                '}';
    }
}
