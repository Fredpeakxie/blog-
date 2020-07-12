package com.fred.web;

import com.fred.json.JsonService;
import com.fred.service.UserService;
import com.fred.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-02 17:32
 */
public class UserDetailUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println("userDetailUpdateServlet");
        String userIdS = req.getParameter("userId");
        String nickname = req.getParameter("nickname");
        String introduction = req.getParameter("introduction");
        int userId = Integer.parseInt(userIdS);
        System.out.println(userId+nickname+introduction);

        //调用service 进行更新
        UserService us = new UserServiceImpl();
        boolean f = us.updateUserDetailStr(userId, nickname, introduction);
        String data = "";
        if(f){
            data = "-1";
        }else {
            data = "1";
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
