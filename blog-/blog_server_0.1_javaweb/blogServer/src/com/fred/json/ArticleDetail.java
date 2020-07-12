package com.fred.json;

import com.fred.bean.Article;
import com.fred.bean.UserDetail;

/**
 * @auther fred
 * @create 2020-05-29 11:13
 */
public class ArticleDetail {
    private Article article;
    private UserDetail userDetail;

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
}
