package com.fred.web;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**接收来自客户端的文章具体页面的请求
 * 并返回 html页面给客户端
 * 请求转发
 * @auther fred
 * @create 2020-05-30 7:25
 */
public class ArticleDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("ArticleDetailServlet");
        //根据对应的的请求跳转至对应的页面
        String blogId = req.getParameter("blogId");
        String blogIdS = String.format("%05d", Integer.parseInt(blogId));
//        System.out.println(blogIdS);
//        req.getRequestDispatcher("/blogs/fb00001.html").forward(req,resp);
        req.getRequestDispatcher("/blogs/fb"+ blogIdS +".html").forward(req,resp);
    }

    @Test
    public void test1(){
        String blogId = "12";
        String blogIdS = String.format("%05d", Integer.parseInt(blogId));
//        System.out.println(blogIdS);
    }
}
