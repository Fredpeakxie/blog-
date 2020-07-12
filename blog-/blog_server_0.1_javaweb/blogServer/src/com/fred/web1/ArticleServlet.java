package com.fred.web1;

import com.fred.file.FileService;
import com.fred.json.JsonService;
import com.fred.service.ArticleService;
import com.fred.service.impl.ArticleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2020-06-06 7:24
 */
public class ArticleServlet extends BaseServlet {

    private ArticleService as = new ArticleServiceImpl();

    /**
     * 发布文章 时调用 申请BlogId并设置文章的 作者号 标题
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void applyArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        System.out.println("publishArticleServlet");
        String userIdS = req.getParameter("userId");
        String title = new String( req.getParameter("title").getBytes("iso8859-1"),"utf-8");

        System.out.println(title);
        int userId = Integer.parseInt(userIdS);
        System.out.println(userId);

        //申请一个博客号
        int articleId = as.addArticle(userId, title);

        String data = "";
        if(articleId > 0){
            data = Integer.toString(articleId);
        }else {
            data = "-1";
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }


    /**
     * 获取文章的html 并启动 文件服务 将其写入磁盘中
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void setHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("articleHtmlSetServlet");
        req.setCharacterEncoding("UTF-8");
        String articleIds = req.getParameter("articleId");
//        String html = req.getParameter("html");
        String html = new String( req.getParameter("html").getBytes("iso8859-1"),"utf-8");
        int articleId = Integer.parseInt(articleIds);
        FileService.writeHtml(articleId,html);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("1");
    }


    /**
     * 对文章的 图片 进行处理并保存
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void setArticlePic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    /**
     * blogId对应的文章页面
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getArticleHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //根据对应的的请求跳转至对应的页面
        String blogId = req.getParameter("blogId");
        String blogIdS = String.format("%05d", Integer.parseInt(blogId));
//        System.out.println(blogIdS);
//        req.getRequestDispatcher("/blogs/fb00001.html").forward(req,resp);
        req.getRequestDispatcher("/blogs/fb"+ blogIdS +".html").forward(req,resp);
    }


    /**
     * 在Msg页面调用
     * 获取文章的简要信息(作者 标题 等) 并以Json的形式发送
     * 以分页的方式发送 获取最后一条发来的信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getArticleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取参数（最后一条观看过的记录）
        String indexS = req.getParameter("index");
        int index = Integer.parseInt(indexS);
//        System.out.println(index);

        String data = "";
        data = JsonService.getArticleDetailsJson(index);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    /**
     * 在Mark页面调用
     * 获取文章的简要信息(作者 标题 等) 并以Json的形式发送
     * 以分页的方式发送 获取最后一条发来的信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getMarkedArticleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取参数（最后一条观看过的记录）
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);
//        System.out.println(index);

        String data = "";
        data = JsonService.getMarkArticleDetailsJson(userId);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }

    protected void getMyArticleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取参数（最后一条观看过的记录）
        String userIdS = req.getParameter("userId");
        int userId = Integer.parseInt(userIdS);
//        System.out.println(index);

        String data = "";
        data = JsonService.getMyArticleDetailsJson(userId);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(data);
    }



}
