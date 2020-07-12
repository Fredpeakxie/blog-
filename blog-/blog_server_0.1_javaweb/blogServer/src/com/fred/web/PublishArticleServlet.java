package com.fred.web;

import com.fred.service.ArticleService;
import com.fred.service.UserService;
import com.fred.service.impl.ArticleServiceImpl;
import com.fred.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-03 14:49
 */
public class PublishArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println("publishArticleServlet");
        String userIdS = req.getParameter("userId");
        String title = req.getParameter("title");

        int userId = Integer.parseInt(userIdS);

        ArticleService as = new ArticleServiceImpl();
        //申请一个博客号
        int articleId = as.addArticle(userId, title);

        String data = "";
        if(articleId > 0){
            data = Integer.toString(articleId);
        }else {
            data = "-1";
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }
}
