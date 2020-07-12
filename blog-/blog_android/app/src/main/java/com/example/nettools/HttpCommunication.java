package com.example.nettools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.bean.Article;
import com.example.bean.ArticleDetail;
import com.example.bean.Comment;
import com.example.bean.UserDetail;
import com.example.jsonservice.ArticleDetailService;
import com.example.jsonservice.ArticleService;
import com.example.jsonservice.CommentService;
import com.example.jsonservice.UserDetailService;
import com.example.utils.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 调用 NetRequset类来实现真正的访问
 * 1 通过函数名 选择对应得URL 连接  不如就把URL 所有的静态常量封装到这个类中
 * 2 处理Activity页面传来的数据
 *
 * 2020/06/06 更新 对于所有的servlet在服务端使用反射技术 通过action方法名找到 对应执行函数 从而完成对服务端web层的结构清晰
 * 对应在客户端的体现就是 不用在每一个网络请求都用一个servlet地址 使用 action字段 访问相应的服务端方法
 * 所指定方法 可以放到静态常量里*
 *
 * 服务器无响应问题 服务器返回空串时 代表无响应 在login 和register中完成无响应返回
 * ？1 难道需要每一个页面的网络访问都做无响应判断？
 * ？2 根据细节 每一个响应的返回内容均有所不同 无响应判断的实现细节也有所不同
 * 试想 在这里进行 获取Application 显示无响应
 *
 */
public class HttpCommunication {
    //部署到远程服务器后 此处更改为域名 并且最好以配置文件 而非硬编码的形式存在
    public static final String BLOG_URL =  "http://192.168.1.101:8080/blogServer";
//    public static final String BLOG_URL =  "http://192.168.43.67:8080/blogServer";
    public static final String USER = "/userServlet";
    public static final String ARTICLE = "/articleServlet";
    public static final String COMMENT = "/commentServlet";
    public static final String UAR = "/uarServlet";
    public static final String IMG_SAVE = "/image/i";


    /********************************************用户操作***************************************/

    /**
     * 用于登录时调用
     * @param username
     * @param password
     * @return
     */
    public static String Login(String username,String password){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","login"));
        list.add(new Pair("username",username));
        list.add(new Pair("password",password));
//        String msg = NetRequest.netGetString(BLOG_URL + LOGIN, list);
        String msg = NetRequest.netGetString(BLOG_URL + USER, list);
        return msg;
    }

    /**
     * 用于注册时调用
     * @param username
     * @param password
     * @param email
     * @return
     */
    public static String Register(String username,String password,String email){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","register"));
        list.add(new Pair("username",username));
        list.add(new Pair("password",password));
        list.add(new Pair("email",email));
//        String msg = NetRequest.netGetString(BLOG_URL + REGISTER, list);
        String msg = NetRequest.netGetString(BLOG_URL + USER, list);
        return msg;
    }

    /**
     * 发送图片至服务端 考虑更名为Set
     * @param picData 客户端 将图片变成base64编码的图片
     * @return
     */
    public static String SetUserHead(int userId,String picData){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","setUserHead"));
        list.add(new Pair("userId",Integer.toString(userId)));
        list.add(new Pair("picData",picData));
//        String html = NetRequest.netGetString(BLOG_URL + SET_USER_HEAD, list);//修改URL
        String html = NetRequest.netGetString(BLOG_URL + USER, list);//修改URL
        return html;
    }

    /**
     * 从服务器获取当前用户ID 对应的 UserDetail
     * @param userId
     * @return
     */
    public static UserDetail GetUserDetail(int userId){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","getUserDetail"));
        list.add(new Pair("userId",Integer.toString(userId)));
//        String jsonString = NetRequest.netGetString(BLOG_URL + USER_DETAIL, list);//修改URL
        String jsonString = NetRequest.netGetString(BLOG_URL + USER, list);//修改URL
        UserDetail userDetail = UserDetailService.getUserDetail(jsonString);
        return userDetail;
    }

    public static boolean SetUserDetail(int userId,String nickname,String introduction){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","updateUserDetail"));
        list.add(new Pair("userId",Integer.toString(userId)));
        list.add(new Pair("nickname",nickname));
        list.add(new Pair("introduction",introduction));
//        String respMsg = NetRequest.netGetString(BLOG_URL + USER_DETAIL_UPDATE, list);//修改URL
        String respMsg = NetRequest.netGetString(BLOG_URL + USER, list);//修改URL
        if ("-1".equals(respMsg)){
            return false;
        }else if("1".equals(respMsg)){
            return true;
        }else {
            return false;
        }
    }

/*****************************************文章操作*****************************************************/
    /**
     * 分页加载
     * @param index 指定 要获取的起始的前一位
     * @return
     */
    public static List<ArticleDetail> GetArticleDetails(int index){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","getArticleList"));
        list.add(new Pair("index",String.valueOf(index)));
//        String jsonString = NetRequest.netGetString(BLOG_URL + BLOGS, list);//修改URL
        String jsonString = NetRequest.netGetString(BLOG_URL + ARTICLE, list);//修改URL
        List<ArticleDetail> adList = ArticleDetailService.getArticleDetails(jsonString);
        return adList;
    }

    /**
     * 完成对详情页面html的请求 发送blogId过去
     * 并从servlet中获取html页面数据
     * @param blogId
     * @return
     */
    public static String GetArticleDetailHtml(int blogId){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","getArticleHtml"));
        list.add(new Pair("blogId",String.valueOf(blogId)));
//        String html = NetRequest.netGetString(BLOG_URL + ARTICLE_DETAIL, list);//修改URL
        String html = NetRequest.netGetString(BLOG_URL + ARTICLE, list);//修改URL
        return html;
    }



    /**
     * 发送 用户id 和标题 申请并注册（在数据库中 该项生成）博客号
     * HTML 页面并未发送 图片也未发送
     * @param userId
     * @param title
     * @return
     */
    public static int ApplyArticleId(int userId,String title){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","applyArticle"));
        list.add(new Pair("userId",Integer.toString(userId)));
        list.add(new Pair("title",title));
//        String respMsg = NetRequest.netGetString(BLOG_URL + PUBLISH, list);//修改URL
        String respMsg = NetRequest.netGetString(BLOG_URL + ARTICLE, list);//修改URL
        int articleId = 0;
        if(respMsg==null||respMsg.equals("")){
            articleId = -1;
        }else {
            articleId = Integer.parseInt(respMsg);
        }
        return articleId;
    }

    //发送 html至服务器
    public static boolean SendHtml(int articleId,String html){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","setHtml"));
        list.add(new Pair("articleId",Integer.toString(articleId)));
        list.add(new Pair("html",html));
//        String respMsg = NetRequest.netGetString(BLOG_URL + SET_ARTICLE_HTML, list);
        String respMsg = NetRequest.netGetString(BLOG_URL + ARTICLE, list);//修改URL
        if ("-1".equals(respMsg)){
            return false;
        }else if("1".equals(respMsg)){
            return true;
        }else {
            return false;
        }
    }

    //发送 html文件对应的图片
    public static boolean SendArticlePics(int articleId,List<String> pics){
        for (int i = 0; i < pics.size(); i++) {
            List<Pair> list = new ArrayList();
            list.add(new Pair("action","setArticlePic"));
            list.add(new Pair("articleId",Integer.toString(articleId)));
            list.add(new Pair("picId",Integer.toString(i+1)));
            list.add(new Pair("picData",pics.get(i)));
//            String respF = NetRequest.netGetString(BLOG_URL + SET_ARTICLE_PIC, list);//修改URL
            String respF = NetRequest.netGetString(BLOG_URL + ARTICLE, list);//修改URL
            if("-1".equals(respF)){
                return false;
            }
        }
        return true;
    }

    /**
     * @param userId 用户id
     * @return ArticleDetail对象 文章简略信息待填
     */
    public static List<ArticleDetail> GetMarkedArticleDetails(int userId){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","getMarkedArticleList"));
        list.add(new Pair("userId",String.valueOf(userId)));
//        String jsonString = NetRequest.netGetString(BLOG_URL + BLOGS, list);//修改URL
        String jsonString = NetRequest.netGetString(BLOG_URL + ARTICLE, list);//修改URL
        List<ArticleDetail> adList = ArticleDetailService.getArticleDetails(jsonString);
        return adList;
    }

    public static List<ArticleDetail> GetMyArticleDetails(int userId){
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","getMyArticleList"));
        list.add(new Pair("userId",String.valueOf(userId)));
        String jsonString = NetRequest.netGetString(BLOG_URL + ARTICLE, list);//修改URL
        List<ArticleDetail> adList = ArticleDetailService.getArticleDetails(jsonString);
        return adList;
    }


/*******************************************用户文章关系***********************************************/

    /**
     * 使数据库更新 文章阅读量
     * @param blogId
     */
    public static void ArticleRead(int blogId) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","articleRead"));//修改action
        list.add(new Pair("blogId",String.valueOf(blogId)));
        NetRequest.netGetString(BLOG_URL + UAR, list);//修改URL
    }

    /**
     * 使数据库更新 文章点赞量
     * @param userId
     * @param blogId
     */
    public static void ArticleLike(int userId,int blogId) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","articleLike"));//修改action
        list.add(new Pair("userId",String.valueOf(userId)));
        list.add(new Pair("blogId",String.valueOf(blogId)));
        NetRequest.netGetString(BLOG_URL + UAR, list);//修改URL
    }

    /**
     * 使数据库更新 文章收藏量
     * @param userId
     * @param blogId
     */
    public static void ArticleMark(int userId,int blogId) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","articleMark"));//修改action
        list.add(new Pair("userId",String.valueOf(userId)));
        list.add(new Pair("blogId",String.valueOf(blogId)));
        NetRequest.netGetString(BLOG_URL + UAR, list);//修改URL
    }

    /**
     * 移除喜欢的文章
     * @param userId
     * @param blogId
     */
    public static void ArticleLikeRemove(int userId, int blogId) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","removeUserLikes"));//修改action
        list.add(new Pair("userId",String.valueOf(userId)));
        list.add(new Pair("blogId",String.valueOf(blogId)));
        NetRequest.netGetString(BLOG_URL + UAR, list);//修改URL
    }
    /**
     * 移除收藏的文章
     * @param userId
     * @param blogId
     */
    public static void ArticleMarkRemove(int userId, int blogId) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","removeUserMarks"));//修改action
        list.add(new Pair("userId",String.valueOf(userId)));
        list.add(new Pair("blogId",String.valueOf(blogId)));
        NetRequest.netGetString(BLOG_URL + UAR, list);//修改URL
    }

    /**
     * 获取数据库中 用户点赞的文章
     * @param userId
     */
    public static List<Integer> GetUserLikes(int userId) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","getUserLikes"));//修改action
        list.add(new Pair("userId",String.valueOf(userId)));
        String userLikes = NetRequest.netGetString(BLOG_URL + UAR, list);//修改URL
        List<Integer> userLike = UserDetailService.getUserLike(userLikes);
        return userLike;
    }

    /**
     * 获取数据库中 用户收藏的文章
     * @param userId
     */
    public static List<Integer> GetUserMarks(int userId) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","getUserMarks"));//修改action
        list.add(new Pair("userId",String.valueOf(userId)));
        String userMarks = NetRequest.netGetString(BLOG_URL + UAR, list);//修改URL
        List<Integer> userMark = UserDetailService.getUserMark(userMarks);
        return userMark;
    }

    /******************************************评论区*******************************************/

    /**
     * 用户评论
     * @param comment
     */
    public static String PublishComment(int userId,int blogId,String comment) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","publishComment"));//修改action
        list.add(new Pair("userId",String.valueOf(userId)));
        list.add(new Pair("blogId",String.valueOf(blogId)));
        list.add(new Pair("comment",comment));
        return NetRequest.netGetString(BLOG_URL + COMMENT, list);//修改URL
    }

    /**
     * 获取用户评论
     * 可能需要获取json对象并进行读取 完成json对象的解析
     * @param blogId
     * @return
     */
    public static List<Comment> GetComments(int blogId) {
        List<Pair> list = new ArrayList();
        list.add(new Pair("action","getComments"));//修改action
        list.add(new Pair("blogId",String.valueOf(blogId)));
        String jsonString =  NetRequest.netGetString(BLOG_URL + COMMENT, list);//修改URL
        List<Comment> comments = CommentService.getComments(jsonString);
        return comments;
    }




/********************************  非servlet式访问 ***********************************/
    /**
     * 非Servlet式访问
     * 从服务器获取当前用户对应的图片 Bitmap
     * @param userId
     * @return
     */
    public static Bitmap GetMipmap(int userId){
        List<Pair> list = new ArrayList();
        String userIds = String.format("%05d", userId);
        byte[] bytes = NetRequest.netGetByte(BLOG_URL + IMG_SAVE + userIds + ".jpg", list);
        if(bytes == null){
            return null;
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        }
    }




    //以后可以使用 带返回值的线程
//    public static UserDetail GetUserDetailT(int userId){
//        List<Pair> list = new ArrayList();
//        list.add(new Pair("userId",Integer.toString(userId)));
//        new Thread( () ->{
//            String jsonString = NetRequest.netGetString(BLOG_URL + USER_DETAIL, list);//修改URL
//        }).start();
//        UserDetail userDetail = UserDetailService.getUserDetail(jsonString);
//        return userDetail;
//    }

    /*public static String NetTest(String url){
        List<Pair> list = new ArrayList();
        String html = NetRequest.netGetString(url, list);//修改URL
        return html;
    }


    public static List<Article> GetBlogs(int index){
        List<Pair> list = new ArrayList();
        list.add(new Pair("index",String.valueOf(index)));
        String jsonString = NetRequest.netGetString(BLOG_URL + BLOGS, list);//修改URL
        List<Article> asList = ArticleService.getArticles("article", jsonString);
        return asList;
    }*/



}

//    /**
//     * 尝试在内部创建线程 并将线程返回给 调用者  调用者只需 使用start方法 即可调用线程
//     * @param handler
//     * @param args
//     */
//    public static void getThread(String respMsg,Handler handler, String... args){
//        new Thread(){
//            @Override
//            public void run() {
//                respMsg = Register("fred3","123456","347667675@qq.com");
////                 respMsg = LoginNet.Login("fred1","123456");
//                handler.sendEmptyMessage(0x01);
//            }
//        };
//    }
