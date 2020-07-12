package com.fred.test;

import com.fred.service.ArticleService;
import com.fred.service.impl.ArticleServiceImpl;

import java.util.Calendar;
import java.util.Date;

/**
 * @auther fred
 * @create 2020-06-03 15:55
 */
public class Test {

    @org.junit.Test
    public void test1(){
//        int userId = 1;
//        String title = "fred24";
//        ArticleService as = new ArticleServiceImpl();
//        int articleId = as.addArticle(userId, title);
//        if(articleId>0){
//            System.out.println(articleId);
//        }
        long s = System.currentTimeMillis();
        double a =  2191669850969445f;
        double b =  589240137f;
        double c = a/b;
        System.out.println(c);
        long end = System.currentTimeMillis();
        System.out.println(end-s);

        Integer e = 0;
        int i = 0;
        System.out.println(i);
    }
}
