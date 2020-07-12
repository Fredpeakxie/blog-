package com.fred.web;

import com.fred.file.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Base64;

/**
 * @auther fred
 * @create 2020-05-31 21:43
 */
public class UserHeadSetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String picData = req.getParameter("picData");
//        System.out.println(picData);

//        getPicFormatBASE64(picData,"E:\\blog_project/user_head_portrait/i00001.jpg");
        //使用工程相对路径失败  猜测是使用相对路径 是完全针对于web访问的
//        getPicFormatBASE64(picData,"/image/i00001.jpg");
        //使用绝对路径成功
        //作为第一个版本 将继续迭代
        FileService.getPicFormatBASE64(picData,userId);

        String data = "";

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }


}
