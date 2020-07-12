package com.example.nettools;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.example.MyApplication;
import com.example.bean.ArticleDetail;
import com.example.bean.Comment;
import com.example.bean.UserDetail;
import com.example.richeditotandroid.ui.RichTextEditActivity;
import com.example.utils.UriTransTools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class NetThread {

    /******************************************************user部分**************************************************/

    public static void Login(String username, String password, Handler handler){
        new Thread(()->{
            String respMsg;
            respMsg = HttpCommunication.Login(username,password);
            Message msg = new Message();
            msg.obj = respMsg;
            if(!respMsg.equals("")){
                msg.what = 0x01;
            }else {
                if(respMsg.equals(-1)){
                    msg.what = 0x02;
                }else {
                    msg.what = 0x03;
                }
            }
            handler.sendMessage(msg);
        }).start();
    }

    public static void Register(String username, String password, String email,Handler handler){
        new Thread(()->{
            String respMsg;
            respMsg = HttpCommunication.Register(username,password,email);
            Message msg = new Message();
            msg.obj = respMsg;
            if(!respMsg.equals("")){
                msg.what = 0x01;
            }else {
                msg.what = 0x02;
            }
            handler.sendMessage(msg);
        }).start();
    }

    public static void SetUserDetail(int userId,String nickName,String introduction,Handler handler){
        new Thread(()->{
            boolean f = HttpCommunication.SetUserDetail(userId, nickName, introduction);
            if (f){
                handler.sendEmptyMessage(0x01);
            }else {
                handler.sendEmptyMessage(0x02);
            }
        }).start();
    }
    public static void GetUserDetail(MyApplication application,Handler handler){
        new Thread( () ->{
            int userId = application.getUser().getUserId();
            UserDetail globalUd = application.getUserDetail();
            UserDetail netUd = HttpCommunication.GetUserDetail(userId);
            globalUd.setAll(netUd);
            Bitmap bitmap = HttpCommunication.GetMipmap(userId);
            Message msg = new Message();
            msg.obj = bitmap;
            msg.what = 0x01;
            handler.sendMessage(msg);
        }).start();
    }

    public static void setUserHead(int userId,String imgToBase64,Handler handler){
        new Thread( () ->{
            //如何写入 非常大的String 跟以前一样？
            HttpCommunication.SetUserHead(userId,imgToBase64);
            handler.sendEmptyMessage(0x02);
        }).start();
    }

    /********************************************article 部分****************************************************/
    public static void GetArticleDetailHtml(int blogId,Handler handler){
        new Thread( ()->{
            //成功使用 请求转发
            String html;
            html = HttpCommunication.GetArticleDetailHtml(blogId);
            //先使用拼接式访问
//            html = HttpCommunication.NetTest("http://192.168.1.101:8080/blogServer/blogs/fb00001.html");
            Message msg = new Message();
            msg.obj = html;
            msg.what = 0x01;
            handler.sendMessage(msg);
        }).start();
    }

    /**
     * 将文章的简要信息加入
     * @param articleDetails
     */
    public static void SetArticlesSimple(List<ArticleDetail> articleDetails){
        for (ArticleDetail ad : articleDetails) {
            int blogId = ad.getArticle().getBlogId();
            //不需要新建线程因为 现在就在线程里
            String html = HttpCommunication.GetArticleDetailHtml(blogId);
            //对html字符串进行解析 使用Jsoup 获取其前三行
            Document doc = Jsoup.parse(html);
            Element body = doc.body();
            String plainText = null;//太多了
            try {
                plainText = body.text().substring(0,140)+"...";
            } catch (StringIndexOutOfBoundsException e) {
                //需要再 上传端口 检测 数量至少大于10
                plainText = body.text();
            }
            //将获取得到的字符串装入ad
            ad.setArticleSimple(plainText);
        }
    }

    public static void GetMarkedArticleDetails(int userId,List<ArticleDetail> articleDetailList,Handler handler ){
        new Thread(()->{
            List<ArticleDetail> articleDetails = HttpCommunication.GetMarkedArticleDetails(userId);
            //从服务器 根据blogId 获取html页面的信息（String）
            SetArticlesSimple(articleDetails);
            articleDetailList.addAll(articleDetails);
            handler.sendEmptyMessage(0x01);
        }).start();
    }

    public static void GetMyArticleDetails(int userId,List<ArticleDetail> articleDetailList,Handler handler ){
        new Thread(()->{
            List<ArticleDetail> articleDetails = HttpCommunication.GetMyArticleDetails(userId);
            //从服务器 根据blogId 获取html页面的信息（String）
            SetArticlesSimple(articleDetails);
            articleDetailList.addAll(articleDetails);
            handler.sendEmptyMessage(0x01);
        }).start();
    }

    public static void GetArticleDetails(int index,List<ArticleDetail> articleDetailList,Handler handler){
        new Thread(()->{
            List<ArticleDetail> articleDetails = HttpCommunication.GetArticleDetails(index);
            articleDetailList.addAll(articleDetails);
            //将文章简要信息 加入
            SetArticlesSimple(articleDetails);
            int newIndex = index + articleDetails.size();
            Message msg = new Message();
            msg.arg1 = newIndex;
            if(index == 0){
                msg.what = 0x01;
            }else if(articleDetails.size()==0){
                msg.what=0x03;
            }else {
                msg.what=0x02;
            }
            handler.sendMessage(msg);
        }).start();
    }

    public static void PublishArticle(int userId, String title, String html, List<Uri> uriList, Context context, Handler handler){
        new Thread(()->{
            //重构这部分代码
            //1 从这边 发送 userID 和title 申请数据库获得
            //选择 去除所有的html先
            int articleId = HttpCommunication.ApplyArticleId(userId, title);
            String articleIdS = String.format("%05d", articleId);
            if(articleId != -1){
                //进行src设置操作 src设置为blogpic/000ArticleId/p001.jpg
                Document doc = Jsoup.parse(html);
                Elements imgs = doc.getElementsByTag("img");
                for (int i = 0; i < imgs.size(); i++){
                    //先使用简单的 文件系统 日后使用 映射来提升查找效率
//                        ../image/JasmineFlower.gif
//                        ../blogpic/boooXXpoX.jpg
                    String picS = String.format("%02d", i+1);
                    String src = "../blogpic/b"+articleIdS+"p"+picS+".jpg";
                    imgs.get(i).attr("src",src);
                }
                String newHtml = doc.toString();
                //对于每张jpg使用base64进行加密 对于每张jpg如何从 html 或程序中读取呢？
                //1传送 html至服务器
                boolean b = HttpCommunication.SendHtml(articleId, newHtml);
                if(b){
                    //2传送每张图片到服务器
                    List<String> pics = new ArrayList<>();
                    for (int i = 0; i < uriList.size(); i++) {
                        Uri uri = uriList.get(i);
                        //使用 工具类 将uri转换成为字符串
                        String pic = UriTransTools.UriTransToBase64String(context, uri);
                        pics.add(pic);
                    }
                    boolean ok = HttpCommunication.SendArticlePics(articleId, pics);
                    if(ok){
                        handler.sendEmptyMessage(0x01);
                    }else {
                        handler.sendEmptyMessage(0x02);
                    }
                    return;
                    //之后进行 跳转至主页面
                }else {
                    handler.sendEmptyMessage(0x02);
                    return;
                }
            }else{
                handler.sendEmptyMessage(0x03);
            }
        }).start();
    }

    /*****************************************用户文章关系*****************************************/
    /**
     * 用户设置界面 用于初始化 用户喜爱的和收藏的文章
     * 注意此处 使用了 Application对象的传入 这种做法 非常不建议 但是 handler只能 传一个 参数 可以使用
     * map 映射 传多个参数 以后待修改
     * @param application
     */
    public static void UserInit(MyApplication application){
        new Thread( () ->{
            List<Integer> userLikes = HttpCommunication.GetUserLikes(application.getUser().getUserId());
            List<Integer> userMarks = HttpCommunication.GetUserMarks(application.getUser().getUserId());
            application.likeArticles.addAll(userLikes);
            application.markArticles.addAll(userMarks);
        }).start();
    }

    public static void ArticleLike(int userId,int blogId){
        new Thread(()->{
            //通过网络发送 使数据库更新
            HttpCommunication.ArticleLike(userId,blogId);
        }).start();
    }

    public static void ArticleLikeRemove(int userId,int blogId){
        new Thread(()->{
            //通过网络发送 使数据库更新
            HttpCommunication.ArticleLikeRemove(userId,blogId);
        }).start();
    }

    public static void ArticleMark(int userId,int blogId){
        new Thread(()->{
            //通过网络发送 使数据库更新
            HttpCommunication.ArticleMark(userId,blogId);
        }).start();
    }

    public static void ArticleMarkRemove(int userId,int blogId){
        new Thread(()->{
            //通过网络发送 使数据库更新
            HttpCommunication.ArticleMarkRemove(userId,blogId);
        }).start();
    }


    /********************************************** 用户评论 **********************************/
    public static void PublishComment(int userId,int blogId,String comment,Handler handler){
        new Thread(()->{
            String s = HttpCommunication.PublishComment(userId, blogId, comment);
            if(s==null||s.equals("")){
                handler.sendEmptyMessage(0x02);
            }else {
                handler.sendEmptyMessage(0x03);
            }
        }).start();
    }


    public static void GetComments(int blogId,Handler handler) {
        new Thread(()->{
            List<Comment> comments = HttpCommunication.GetComments(blogId);
            Message msg = new Message();
            msg.obj = comments;
            if(comments != null){
                msg.what = 0x04;
            }else {
                msg.what = 0x05;
            }
            handler.sendMessage(msg);
        }).start();
    }
}