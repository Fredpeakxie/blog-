package com.fred.json;

import com.fred.bean.Article;
import com.fred.bean.User;
import com.fred.bean.UserDetail;
import com.fred.service.ArticleService;
import com.fred.service.UserService;
import com.fred.service.impl.ArticleServiceImpl;
import com.fred.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther fred
 * @create 2020-05-27 8:34
 */
public class JsonService {
    public static String getArticleDetailsJson(int index){
        //1获取Service对象
        ArticleService as = new ArticleServiceImpl();
        UserService us = new UserServiceImpl();
        //2获取 Article对象
        List<Article> articleList = as.getArticles(index);
        List<ArticleDetail> articleDetailList = new ArrayList<>();
        for ( Article article : articleList) {
            Integer userId = article.getUserId();
            UserDetail detail = us.findUserDetailById(userId);
            articleDetailList.add(new ArticleDetail(article,detail));
        }
        return JsonTools.createJsonString("articleDetails",articleDetailList);
    }


    public static String getMarkArticleDetailsJson(int userId){
        //1获取Service对象
        ArticleService as = new ArticleServiceImpl();
        UserService us = new UserServiceImpl();
        //2获取 Article对象
        List<Article> articleList = as.getMarkedArticles(userId);
        List<ArticleDetail> articleDetailList = new ArrayList<>();
        for ( Article article : articleList) {
            Integer userIdT = article.getUserId();
            UserDetail detail = us.findUserDetailById(userId);
            articleDetailList.add(new ArticleDetail(article,detail));
        }
        return JsonTools.createJsonString("articleDetails",articleDetailList);
    }

    public static String getMyArticleDetailsJson(int userId){
        //1获取Service对象
        ArticleService as = new ArticleServiceImpl();
        UserService us = new UserServiceImpl();
        //2获取 Article对象
        List<Article> articleList = as.getMyArticles(userId);
        List<ArticleDetail> articleDetailList = new ArrayList<>();
        System.out.println(userId);
        for ( Article article : articleList) {
            Integer userIdT = article.getUserId();
            UserDetail detail = us.findUserDetailById(userId);
            articleDetailList.add(new ArticleDetail(article,detail));
        }
        return JsonTools.createJsonString("articleDetails",articleDetailList);
    }

    public static String getUserDetailJson(int userId){
        UserService us = new UserServiceImpl();
        UserDetail userDetail = us.findUserDetailById(userId);
        return JsonTools.createJsonString("userDetail",userDetail);
    }


}
