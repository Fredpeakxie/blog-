package com.example.activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MyApplication;
import com.example.adapter.Adapters;
import com.example.adapter.CommentAdapter;
import com.example.bean.Comment;
import com.example.blog.R;
import com.example.nettools.HttpCommunication;
import com.example.nettools.NetThread;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ArticleDetailActivity extends Activity {
    private Context mContext = this;
    private TextView tvTitle,tvUserNickname,tvReadNum,tvLikeNum,tvMarkNum;
    private EditText etComment;
    private WebView wvArticleDetail;
    private ImageButton btnLike,btnMark;
    private Button btnComment;
    private int blogId,userId,myUserId,readNum,likeNum,markNum;
    private String html;
    boolean liked,marked;
    private Comment lastComment;

    private ListView commentListView ;
    private List<Comment> commentList = new ArrayList<>();
    private CommentAdapter commentAdapter;

   private static class MyHandler extends Handler{
        private WeakReference<ArticleDetailActivity> adActivity;
        MyHandler(WeakReference<ArticleDetailActivity> adActivity){
            this.adActivity = adActivity;
        }

        public void handleMessage(@NonNull Message msg){
            ArticleDetailActivity articleDetailActivity = adActivity.get();
            switch (msg.what){
                case 0x01:
                    //3接收到返回的页面并把它从webView中加载出来 由Handler 来接收 网络消息 结束响应并处理
                    articleDetailActivity.html = (String) msg.obj;
                    articleDetailActivity.wvArticleDetail.loadDataWithBaseURL(HttpCommunication.BLOG_URL+"/blogs/fb00001.html",adActivity.get().html,"text/html","UTF-8","");
                    break;
                case 0x02:
                    Toast.makeText(articleDetailActivity,"网络出错 发送失败",Toast.LENGTH_SHORT).show();
                    break;
                case 0x03:
                    Toast.makeText(articleDetailActivity,"发表评论成功",Toast.LENGTH_SHORT).show();
                    articleDetailActivity.etComment.setText("");
                    articleDetailActivity.commentList.add(articleDetailActivity.lastComment);
                    articleDetailActivity.commentAdapter.notifyDataSetChanged();
                    Adapters.setListViewHeightBasedOnChildren(articleDetailActivity.commentListView);
                    InputMethodManager imm = (InputMethodManager) articleDetailActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(articleDetailActivity.getWindow().getDecorView().getWindowToken(), 0);
                    break;
                case 0x04 :
                    List<Comment> commentList = (List<Comment>) msg.obj;
                    Log.d(TAG, "handleMessage: "+commentList.toString());
                    articleDetailActivity.commentList.addAll(commentList);
                    articleDetailActivity.commentAdapter.notifyDataSetChanged();
                    Adapters.setListViewHeightBasedOnChildren(articleDetailActivity.commentListView);
                    break;
                case 0x05:
                    Toast.makeText(articleDetailActivity,"评论区加载失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    private MyHandler myHandler = new MyHandler(new WeakReference<>(this));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        //1注册组件 接受消息并添加到组件上
        setView();
        //2通过blogId 作为参数访问服务器并接受服务器返回的页面 使用多线程
        NetThread.GetArticleDetailHtml(blogId,myHandler);

        //4初始化评论数据
        initCommentData();

        //完成参数载入到Adapter中
        setViewAfterInit();
    }

    /**
     * 完成对msgFragment消息（其中包括文章作者的一些信息）的接收
     * 注册基本组件
     */
    private void setView(){
        Log.d(TAG, "setView: "+Thread.currentThread().getName());
        //使作者头部 和 webView具体内容共同呈现
        Intent intent = getIntent();
//        Bundle bundle = this.getIntent().getExtras();
//        ArticleDetail articleDetail = (ArticleDetail)bundle.get("articleDetail");
        String title = intent.getStringExtra("title");
        String userNickname = intent.getStringExtra("userNickname");
        //如果什么都没有找到就返回-1
        blogId = intent.getIntExtra("blogId",-1);
        userId = intent.getIntExtra("userId",-1);
        readNum = intent.getIntExtra("readNum",-1);
        likeNum = intent.getIntExtra("likeNum",-1);
        markNum = intent.getIntExtra("markNum",-1);

        tvTitle = findViewById(R.id.activity_ad_title);
        tvTitle.setText(title);
        tvUserNickname = findViewById(R.id.activity_ad_userNickname);
        tvUserNickname.setText(userNickname);

        tvReadNum = findViewById(R.id.activity_ad_readNum);
        tvReadNum.setText(Integer.toString(readNum+1));
        tvLikeNum = findViewById(R.id.activity_ad_likeNum);
        tvLikeNum.setText(Integer.toString(likeNum));
        tvMarkNum = findViewById(R.id.activity_ad_markNum);
        tvMarkNum.setText(Integer.toString(markNum));

        MyApplication application = (MyApplication)getApplication();
        btnLike = findViewById(R.id.article_detail_a_btn_like);
        //做blogId与application中 存储的articleLikes 查找
        liked = application.likeArticles.contains(blogId);
        myUserId = application.getUser().getUserId();
        if(liked){
            btnLike.setImageResource(R.mipmap.like_pink);
        }

        btnLike.setOnClickListener(v -> {
            ArticleDetailActivity ada = ArticleDetailActivity.this;
            if(!liked){
                application.likeArticles.add(blogId);
                btnLike.setImageResource(R.mipmap.like_pink);
                NetThread.ArticleLike(myUserId,blogId);
                ada.likeNum++;
            }else {
                application.likeArticles.remove((Object)blogId);
                NetThread.ArticleLikeRemove(myUserId,blogId);
                btnLike.setImageResource(R.mipmap.like);
                ada.likeNum--;
            }
            resetView();
            ada.liked = !liked;
        });

        btnMark = findViewById(R.id.article_detail_a_btn_mark);
        marked = application.markArticles.contains(blogId);
        if(marked){
            btnMark.setImageResource(R.mipmap.mark2_yellow);
        }
        btnMark.setOnClickListener(v -> {
            ArticleDetailActivity ada = ArticleDetailActivity.this;
            if(!marked){
                application.markArticles.add(blogId);
                btnMark.setImageResource(R.mipmap.mark2_yellow);
                NetThread.ArticleMark(myUserId,blogId);
                ada.markNum++;
            }else {
                application.markArticles.remove((Object)blogId);
                NetThread.ArticleMarkRemove(myUserId,blogId);
                btnMark.setImageResource(R.mipmap.mark2);
                ada.markNum--;
            }
            resetView();
            ada.marked = !marked;
        });

        etComment = findViewById(R.id.article_detail_a_et_comment);
        btnComment = findViewById(R.id.article_detail_a_btn_comment);
        btnComment.setOnClickListener((v)->{
            String comment = etComment.getText().toString();
            lastComment = new Comment();
            lastComment.setComment(comment);
            lastComment.setUserNickName(userNickname);
            NetThread.PublishComment(myUserId,blogId,comment,myHandler);
        });

//        Toast.makeText(this,title + userNickname + readNum + likeNum + markNum,Toast.LENGTH_LONG).show();
//        Log.d("ADActivity", "setView: "+title+userNickname);

        //webView 部分
//        wvHtml = findViewById(R.id.html);
        wvArticleDetail = findViewById(R.id.activity_ad_webView);
        WebSettings webSettings = wvArticleDetail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvArticleDetail.setWebViewClient(new MyWebViewClient());
//        wvArticleDetail.addView();
    }

    private void initCommentData(){
        NetThread.GetComments(blogId,myHandler);
    }

    private void setViewAfterInit(){
        commentListView = findViewById(R.id.list_comment);
        commentAdapter = new CommentAdapter(this,R.layout.item_comment,commentList);
        Log.d(TAG, "setViewAfterInit: "+commentAdapter);
        commentListView.setAdapter(commentAdapter);
    }

    public void resetView(){
        tvLikeNum.setText(Integer.toString(likeNum));
        tvMarkNum.setText(Integer.toString(markNum));
    }

    /**
     * 通过导入Jsoup库来实现html页面内容的解析
     * 替换所有img标签的宽高属性
     * @param htmltext
     * @return
     */
    public static String getNewContent(String htmltext){
        try{
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element: elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        /**
         * 当页面加载完成后 实现对页面中图片尺寸的替换
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view,String url){
            super.onPageFinished(view,url);
            imgReset();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        private void imgReset() {
            wvArticleDetail.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                    "}" +
                    "})()");
        }
    }
}


//                adActivity.get().wvHtml.setText(adActivity.get().html);
//无法获取图片要素
//                adActivity.get().wvArticleDetail.loadDataWithBaseURL(HttpCommunication.BLOG_URL,adActivity.get().html,"text/html","UTF-8","");
//成功获取图片
//                String newContent = getNewContent(adActivity.get().html);
//                adActivity.get().wvArticleDetail.loadDataWithBaseURL(HttpCommunication.BLOG_URL,newContent,"text/html","UTF-8","");
//                    adActivity.get().html = (String) msg.obj;
//                            adActivity.get().wvArticleDetail.loadDataWithBaseURL(HttpCommunication.BLOG_URL+"/blogs/fb00001.html",adActivity.get().html,"text/html","UTF-8","");
//成功获取图片
//                adActivity.get().wvArticleDetail.loadUrl(HttpCommunication.BLOG_URL+"/blogs/fb00001.html");
