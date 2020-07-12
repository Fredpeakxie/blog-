package com.fred.web;

import com.fred.bean.Article;
import com.fred.json.JsonService;
import com.fred.json.JsonTools;
import com.fred.service.ArticleService;
import com.fred.service.impl.ArticleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @auther fred
 * @create 2020-05-28 21:19
 */
public class MsgFServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取参数（最后一条观看过的记录）
        String indexS = req.getParameter("index");
        int index = Integer.parseInt(indexS);
        System.out.println(index);

        String data = "";
        data = JsonService.getArticleDetailsJson(index);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
