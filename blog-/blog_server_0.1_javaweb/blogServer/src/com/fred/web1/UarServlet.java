package com.fred.web1;

import com.fred.bean.UserArticleRelation;
import com.fred.service.UarService;
import com.fred.service.impl.UarServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-13 6:37
 */
public class UarServlet extends BaseServlet {

    private UarService uarService = new UarServiceImpl();
    /**
     * 更新指定blogId的阅读量
     * 参数 blogId
     * @throws ServletException
     * @throws IOException
     */
    protected void articleRead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String blogIdS = req.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdS);

        uarService.readAdd(blogId);

        String data = "";

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    /**
     * 更新指定blogId的点赞量
     * 参数 blogId
     * @throws ServletException
     * @throws IOException
     */
    protected void articleLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        System.out.println("articleLike");
        String blogIdS = req.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdS);
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);

        uarService.likeAdd(userId,blogId);

        String data = "";

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    /**
     * 更新指定blogId的收藏量
     * 参数 blogId
     * @throws ServletException
     * @throws IOException
     */
    protected void articleMark(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        System.out.println("articleMark");
        String blogIdS = req.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdS);
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);

        uarService.markAdd(userId,blogId);

        String data = "";

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }


    /**
     * 移除用户喜欢的文章
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void removeUserLikes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);
        String blogIdS = req.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdS);

        System.out.println("remove like"+userIdS+"   " + blogId);
        String data = "";
        uarService.removeLike(new UserArticleRelation(userId,blogId));

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    /**
     * 移除用户收藏的文章
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void removeUserMarks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);
        String blogIdS = req.getParameter("blogId");
        int blogId = Integer.parseInt(blogIdS);

        System.out.println("remove mark"+userIdS+"   " + blogId);
        String data = "";
        uarService.removeMark(new UserArticleRelation(userId,blogId));


        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }



    /**
     * 获取用户喜欢的文章
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserLikes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);

        String data = "";
        data += uarService.getLikes(userId);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    /**
     * 获取用户收藏的文章
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserMarks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);

        String data = "";
        data += uarService.getMarks(userId);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }
}
