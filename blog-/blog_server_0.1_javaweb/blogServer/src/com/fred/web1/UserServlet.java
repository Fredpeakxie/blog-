package com.fred.web1;

import com.fred.bean.User;
import com.fred.bean.UserArticleRelation;
import com.fred.file.FileService;
import com.fred.json.JsonService;
import com.fred.service.UserService;
import com.fred.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-06 7:48
 */
public class UserServlet extends BaseServlet{
    private UserService userService = new UserServiceImpl();

    /**
     * 用户注册
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    /**
     * 用户登录
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username+ "  login");
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

    /**
     * 用户头像设置
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void setUserHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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


    /**
     * 获取用户的详情数据 并且以json的形式返回
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);
        String userDetailJson = JsonService.getUserDetailJson(userId);
        String data = userDetailJson;
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    /**
     * 更新用户的详情信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateUserDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userIdS = req.getParameter("userId");
//        String nickname = req.getParameter("nickname");
        String nickname = new String( req.getParameter("nickname").getBytes("iso8859-1"),"utf-8");
//        String introduction = req.getParameter("introduction");
        String introduction = new String( req.getParameter("introduction").getBytes("iso8859-1"),"utf-8");
        int userId = Integer.parseInt(userIdS);
//        System.out.println(userId+nickname+introduction);

        //调用service 进行更新

        boolean f = userService.updateUserDetailStr(userId, nickname, introduction);
        String data = "";
        if(f){
            data = "-1";
        }else {
            data = "1";
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }
}
