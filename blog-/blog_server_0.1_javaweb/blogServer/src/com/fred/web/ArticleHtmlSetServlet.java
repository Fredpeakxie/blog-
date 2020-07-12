package com.fred.web;

import com.fred.file.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-03 22:42
 */
public class ArticleHtmlSetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("articleHtmlSetServlet");
        req.setCharacterEncoding("UTF-8");
        String articleIds = req.getParameter("articleId");
        String html = req.getParameter("html");
        int articleId = Integer.parseInt(articleIds);
        FileService.writeHtml(articleId,html);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("1");
    }
}
