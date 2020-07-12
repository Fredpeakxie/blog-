package com.example.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.MyApplication;
import com.example.adapter.ArticleDetailAdapter;
import com.example.bean.ArticleDetail;
import com.example.blog.R;
import com.example.nettools.NetThread;
import com.example.utils.MyListener;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MarkFragment extends Fragment {
    private ListView listView;
    private List<ArticleDetail> articleDetailList = new ArrayList<>();
    private ArticleDetailAdapter articleDetailAdapter;

    private static class MyHandler extends Handler {
        private WeakReference<MarkFragment> markFragment;
        MyHandler(WeakReference<MarkFragment> markFragment){
            this.markFragment = markFragment;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    this.markFragment.get().articleDetailAdapter.notifyDataSetChanged();
                    this.markFragment.get().listView.smoothScrollToPosition(0);
                    break;
                case 0x02:
                    this.markFragment.get().articleDetailAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }
    private MyHandler handler = new MyHandler(new WeakReference<>(this));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = setView(inflater);
        initData();
        articleDetailAdapter = new ArticleDetailAdapter(getActivity(),R.layout.item_article_detail,articleDetailList);
        listView.setAdapter(articleDetailAdapter);
        listView.setOnItemClickListener(new MyListener(listView,getContext()));
        return view;
    }

    private View setView(@NonNull LayoutInflater inflater){
        View view = inflater.inflate(R.layout.fragment_mark,null);
        listView = view.findViewById(R.id.list_mark);
        return view;
    }

    private void initData(){
        int userId = ((MyApplication)getActivity().getApplication()).getUser().getUserId();
        NetThread.GetMarkedArticleDetails(userId,articleDetailList,handler);
    }
}
