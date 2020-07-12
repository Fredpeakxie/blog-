package com.fred.web;

import com.fred.file.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-04 7:14
 */
public class ArticlePicSetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String articleIds = req.getParameter("articleId");
        String picIds = req.getParameter("picId");
        String picData = req.getParameter("picData");

        int articleId = Integer.parseInt(articleIds);
        int picId = Integer.parseInt(picIds);

        Boolean ok = FileService.setArticlePic(articleId, picId, picData);

        String data = "";
        if(ok){
            data = "1";
        }else {
            data = "-1";
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);

    }
}
