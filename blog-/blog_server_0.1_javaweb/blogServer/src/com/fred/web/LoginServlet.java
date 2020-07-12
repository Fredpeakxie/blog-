package com.fred.web;

import com.fred.bean.User;
import com.fred.service.UserService;
import com.fred.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @auther fred
 * @create 2020-05-21 13:11
 */
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User(null,username,password,null);
        User login = userService.login(user);
        String data = "";
        if(login != null){
             data = login.getUserId().toString();
        }else {
            data = Integer.toString(-1);
        }
        //对resp中的data编码进行设置
        //设想对象传递
        //使用json传递 并不了解
        //实在学不会 使用 string传递 并解析
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
//        // 对数据进行压缩:
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        GZIPOutputStream gout = new GZIPOutputStream(bout);
//        gout.write(data.getBytes());
//        gout.close();
//        // 得到压缩后的数据
//        byte gdata[] = bout.toByteArray();
//        resp.setHeader("Content-Encoding", "gzip");
//        resp.setHeader("Content-Length", gdata.length + "");
//        resp.getOutputStream().write(gdata);
