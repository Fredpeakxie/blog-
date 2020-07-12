package com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.activity.ArticleDetailActivity;
import com.example.bean.Article;
import com.example.bean.ArticleDetail;
import com.example.bean.UserDetail;
import com.example.nettools.HttpCommunication;

public class MyListener implements AdapterView.OnItemClickListener{
    private ListView listView;
    private Context context;

    public MyListener(ListView listView, Context context) {
        this.listView = listView;
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取被点击的对象的数据模型
        ArticleDetail articleDetail = (ArticleDetail)listView.getAdapter().getItem(position);
        //告诉服务器阅读此文章 2020.6.10
        new Thread(()->{
            //因为没有连接到服务器后面的读也不可能发生 所以简化 消息传递
            HttpCommunication.ArticleRead(articleDetail.getArticle().getBlogId());
        }).start();

        //不传递数据的
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        //数据传输
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("articleDetail",articleDetail);
//        intent.putExtras(bundle);
        Article article =  articleDetail.getArticle();
        intent.putExtra("blogId",article.getBlogId());
        intent.putExtra("title",article.getTitle());
        intent.putExtra("readNum",article.getReadNum());
        intent.putExtra("likeNum",article.getLikeNum());
        intent.putExtra("markNum",article.getMarkNum());
        UserDetail userDetail = articleDetail.getUserDetail();
        intent.putExtra("userId",userDetail.getUserId());
        intent.putExtra("userNickname",userDetail.getNickname());
        context.startActivity(intent);
//                Toast.makeText(getContext(),articleDetail.toString(),Toast.LENGTH_LONG).show();测试点击事件的响应状况
    }
}