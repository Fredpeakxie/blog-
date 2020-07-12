package com.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.MyApplication;
import com.example.adapter.ArticleDetailAdapter;
import com.example.bean.ArticleDetail;
import com.example.blog.R;
import com.example.nettools.NetThread;
import com.example.utils.MyListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MyArticleActivity extends Activity {

    private ListView listView;
    private List<ArticleDetail> articleDetailList = new ArrayList<>();
    private ArticleDetailAdapter articleDetailAdapter;

    private static class MyHandler extends Handler {
        private WeakReference<MyArticleActivity> myArticleActivity;
        MyHandler(WeakReference<MyArticleActivity> myArticleActivity){
            this.myArticleActivity = myArticleActivity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    this.myArticleActivity.get().articleDetailAdapter.notifyDataSetChanged();
                    this.myArticleActivity.get().listView.smoothScrollToPosition(0);
                    break;
                case 0x02:
                    this.myArticleActivity.get().articleDetailAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }
    private MyHandler handler = new MyHandler(new WeakReference<>(this));
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_article);

        setView();

        initData();

        articleDetailAdapter = new ArticleDetailAdapter(this,R.layout.item_article_detail,articleDetailList);
        listView.setAdapter(articleDetailAdapter);
        listView.setOnItemClickListener(new MyListener(listView,this));
    }

    private void setView(){
        listView = findViewById(R.id.list_my_article);
    }

    private void initData(){
        int userId = ((MyApplication)getApplication()).getUser().getUserId();
        NetThread.GetMyArticleDetails(userId,articleDetailList,handler);
    }
}
