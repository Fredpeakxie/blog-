package com.fred.json;

import com.fred.bean.*;
import com.fred.service.ArticleService;
import com.fred.service.UserService;
import com.fred.service.impl.ArticleServiceImpl;
import com.fred.service.impl.UserServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther fred
 * @create 2020-05-27 10:10
 */
public class JsonTest1 {
//    @Test
//    public void test1(){
//        String msg1;
//        JsonService jsonService = new JsonService();
//        User user = jsonService.getUser();
//        msg1 = JsonTools.createJsonString("user",user);
//        System.out.println(msg1);
//    }
    @Test
    public void test2(){
        String msg1;
        ArticleService as = new ArticleServiceImpl();
        List<Article> articleList = as.getArticles(0);
        msg1 = JsonTools.createJsonString("article",articleList);
        System.out.println(msg1);
    }


//    @Test
//    public void test3(){
//        String msg1;
//        ArticleService as = new ArticleServiceImpl();
//        UserService us = new UserServiceImpl();
//        List<Article> articleList = as.getArticle(0);
//        List<ArticleDetail> articleDetailList = new ArrayList<>();
//        for ( Article article : articleList) {
//            Integer userId = article.getUserId();
//            UserDetail detail = us.findUserDetailById(userId);
//            String articleJ = JsonTools.createJsonString("articleJ", article);
//            String userDetailJ = JsonTools.createJsonString("userDetailJ", detail);
//            System.out.println(articleJ);
//            System.out.println(userDetailJ);
//            articleDetailList.add(new ArticleDetail(articleJ,userDetailJ));
//        }
//        msg1 = JsonTools.createJsonString("articleDetail",articleDetailList);
//        System.out.println(msg1);
//    }


    @Test
    public void test4(){
        String msg1;
        ArticleService as = new ArticleServiceImpl();
        UserService us = new UserServiceImpl();
        List<Article> articleList = as.getArticles(0);
        List<ArticleDetail> articleDetailList = new ArrayList<>();
        for ( Article article : articleList) {
            Integer userId = article.getUserId();
            UserDetail detail = us.findUserDetailById(userId);
            articleDetailList.add(new ArticleDetail(article,detail));
        }
        msg1 = JsonTools.createJsonString("articleDetail",articleDetailList);
        System.out.println(msg1);
    }

    @Test
    public void test5(){
        String msg = JsonService.getArticleDetailsJson(0);
        System.out.println(msg);
    }

    /*{"articleDetail":[
    {"article":{"blogId":19,"likeNum":20,"markNum":0,"readNum":19,"title":"fred18","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":18,"likeNum":19,"markNum":0,"readNum":18,"title":"fred17","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":17,"likeNum":18,"markNum":0,"readNum":17,"title":"fred16","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":16,"likeNum":17,"markNum":0,"readNum":16,"title":"fred15","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":15,"likeNum":16,"markNum":0,"readNum":15,"title":"fred14","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":14,"likeNum":15,"markNum":0,"readNum":14,"title":"fred13","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":13,"likeNum":14,"markNum":0,"readNum":13,"title":"fred12","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":12,"likeNum":13,"markNum":0,"readNum":12,"title":"fred11","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":11,"likeNum":12,"markNum":0,"readNum":11,"title":"fred10","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":10,"likeNum":11,"markNum":0,"readNum":10,"title":"fred9","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}}]}
*/
}
