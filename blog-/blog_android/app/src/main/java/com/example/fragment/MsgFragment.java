package com.example.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adapter.ArticleDetailAdapter;
import com.example.bean.ArticleDetail;
import com.example.blog.R;
import com.example.nettools.NetThread;
import com.example.utils.MyListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MsgFragment extends Fragment {
    private ListView listView;
    private List<ArticleDetail> articleDetailList = new ArrayList<>();
    private ArticleDetailAdapter articleDetailAdapter;
    private View footer;
    private TextView tvFooter;
    private int index;
//    private int lastItem; 目前还用不上

    private static class MyHandler extends Handler {
        private WeakReference<MsgFragment> msgFragment;
        MyHandler(WeakReference<MsgFragment> msgFragment){
            this.msgFragment = msgFragment;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            this.msgFragment.get().index = msg.arg1;
            switch (msg.what){
                case 0x01:
                    this.msgFragment.get().articleDetailAdapter.notifyDataSetChanged();
                    this.msgFragment.get().listView.smoothScrollToPosition(0);
                    break;
                case 0x02:
                    this.msgFragment.get().articleDetailAdapter.notifyDataSetChanged();
                    break;
                case 0x03:
                    String text = "已经没有了";
                    TextView textView = this.msgFragment.get().tvFooter;
                    textView.setText(text);
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
        //初始化一些组件
        View view = setView(inflater);
        //获取用户喜爱的 和关注的 数据
//        userInit(); 因为会一开始就被执行 所以放到 Login结束前做
        //初始化前十个数据
        initData();
        //创建Adapter从传入数据由adapter来完成对数据的访问 并将数据填入到控件中 完成 MVC的control部分
        setViewAfterInit();
        return view;
    }

    private View setView(@NonNull LayoutInflater inflater){
        Log.d(TAG, "setView: "+Thread.currentThread().getName());
        View view = inflater.inflate(R.layout.fragment_msg,null);
        listView = view.findViewById(R.id.list_msg);
        footer = inflater.inflate(R.layout.footer_view,null);
        //在使用fVB函数时 使用组件所在的xml文档生成的对象去加载
        tvFooter = footer.findViewById(R.id.tv_footer);
        listView.addFooterView(footer);
        return view;
    }

    private void setViewAfterInit(){
        articleDetailAdapter = new ArticleDetailAdapter(getActivity(),R.layout.item_article_detail,articleDetailList);
        listView.setAdapter(articleDetailAdapter);
        listView.setOnItemClickListener(new MyListener(listView,getContext()));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    if(view.getLastVisiblePosition() == view.getCount()-1){
                        loadData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
    }

    private void initData(){
        //下次可以试试 把所有网络通信所涉及到的线程创建
        //都封装到静态工具类中 再由工具类完成向一个自建的通用的handler传递消息
        //从而实现网络访问线程和线程间通讯
        NetThread.GetArticleDetails(0,articleDetailList,handler);
        //同样我认为在这边也需要handler来进行线程间消息通讯
        //在这里告诉服务器我要从第i+1条到第i+10条（服务器默认一次发10条过来）的数据
    }

    private void loadData(){
        NetThread.GetArticleDetails(index,articleDetailList,handler);
    }
}




//    private List<Article> articleList = new ArrayList<>();
//    private ArticleAdapter articleAdapter;


//        articleAdapter = new ArticleAdapter(getActivity(),R.layout.item_article_detail, articleList);
//        listView.setAdapter(articleAdapter);

//                List<Article> articles = HttpCommunication.GetBlogs(0);
//                articleList.addAll(articles);

//                List<Article> articles = HttpCommunication.GetBlogs(index);
//                articleList.addAll(articles);

//        for (int i = 0; i < 20; i++) {
//            User user = new User("fred"+i,"email"+i,i);
//            Article as = new Article(user,"title" + i,"content"+i);
//            articleList.add(as);
//        }


//            User user = new User("fred"+i,"email"+i,i);
//            Article as = new Article(user,"title" + i,"content"+i);
//            articleList.add(as);
//            通知adapter
//            asAdapter.notifyDataSetChanged();

/* if(msg.what == 0x01){
                //网络通信进程完成对服务端数据的解析并 加入到 articleSimpleList中
                this.msgFragment.get().asAdapter.notifyDataSetChanged();
            }else {
                this.msgFragment.get().tvFooter.setText("已经没有了");
            }*/
