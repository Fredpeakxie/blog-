package com.fred.web1;

import com.fred.bean.Comment;
import com.fred.service.CommentService;
import com.fred.service.impl.CommentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-12 8:35
 */
public class CommentServlet extends BaseServlet {
    private CommentService commentService = new CommentServiceImpl();

    protected void publishComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);
        String blogIdS = req.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdS);

        String comment = new String( req.getParameter("comment").getBytes("iso8859-1"),"utf-8");

        boolean f = commentService.publishComment(new Comment(blogId,userId,comment));
        System.out.println(f);
        String data = "";
        if(f){
            data = "1";
        }else {
            data = "-1";
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    protected void getComments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String blogIdS = req.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdS);

        String commentsJson = commentService.getCommentsJson(blogId);
        String data = "";
        if(commentsJson != null){
            data = commentsJson;
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }
}
