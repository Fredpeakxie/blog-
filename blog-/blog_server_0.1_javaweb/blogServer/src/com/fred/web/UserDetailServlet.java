package com.fred.web;

import com.fred.json.JsonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-02 7:46
 */
public class UserDetailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);
        String userDetailJson = JsonService.getUserDetailJson(userId);
        String data = userDetailJson;
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
