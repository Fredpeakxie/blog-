package com.fred.web;

import com.fred.bean.User;
import com.fred.service.UserService;
import com.fred.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-05-22 10:20
 */
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受来自 移动端的 username password email
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String data = "";
        //如果接受到对方的username重复 则报告用户
        //用户名重复 更换用户名再尝试注册
        if (userService.existUsername(username)){
            data += "用户名重复 更换用户名再尝试注册";
        }else {
            //用户名正确 注册用户
            User user = new User(null, username, password, email);
            userService.registerUser(user);
            data += "注册成功";
        }
        //将数据写入
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }
}
