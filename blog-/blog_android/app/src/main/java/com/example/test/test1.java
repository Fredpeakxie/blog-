package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.blog.R;

import java.util.ArrayList;
import java.util.List;

/*public class MainActivity1 extends Activity {
    private ListView listview;
    private List<String> datas;
    private LayoutInflater inflater;
    private MyAdapter adapter;
    private List<String>  contents;
    private int count = 0;
    private View footView;
    private Handler handler = new Handler();
    int lastItem;
    private RelativeLayout loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        inflater = LayoutInflater.from(this);
        listview = (ListView) findViewById(R.id.list_msg);
        adapter = new MyAdapter();
        footView = inflater.inflate(R.layout.footer, null);
        loading = (RelativeLayout) footView.findViewById(R.id.loading);
        //listview的addFooterView()添加view到listview底部一定要加在listview.setAdapter(adapter);这代码前面
        listview.addFooterView(footView);
        listview.setAdapter(adapter);
        //添加listview滚动监听
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            //AbsListView view 这个view对象就是listview
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        loadData();
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1 ;
            }
        });
    }
    protected void loadData() {
        loading.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load();
                loading.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        }, 5000);
    }
    protected void load() {
        int count
                = adapter.getCount() + 1;
        for(int i
            = count; i < count + 20; i++) {  //每次加载20条数据
            contents.add("加载数据:::" +
                    i);
        }
    }
    private void initData() {
        contents = new ArrayList<String>();
        for(int i
            = 1; i < 20;
            i++){
            contents.add("加载数据:::" +
                    i);
        }
    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return contents.size();
        }
        @Override
        public Object getItem(int position) {
            return contents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView
                    == null){
                convertView = inflater.inflate(R.layout.item, parent, false);
                holder = new ViewHolder();
                holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(contents.get(position));
            return convertView;
        }
        class ViewHolder{
            TextView tvContent;
        }
    }
}*/
public class test1{

}