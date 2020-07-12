package com.fred.service;

import com.fred.bean.Article;

import java.util.List;

/**
 * @auther fred
 * @create 2020-05-28 11:08
 */
public interface ArticleService {

    /**
     * 用户客户端发送一个之前访问的最后一个 index
     * @param index
     * @return 返回从index+1到index+10 之间的article
     */
    public List<Article> getArticles(int index);

    public List<Article> getMarkedArticles(int userId);

    public List<Article> getMyArticles(int userId);

    public int addArticle(int userId,String title);

}
