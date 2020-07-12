package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.bean.Article;
import com.example.blog.R;

import java.util.List;
@Deprecated
public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    /*private int resourceId;
    static class ViewHolder{
        private TextView title;
        private TextView username;
        private TextView sContent;
    }

    public ArticleAdapter(Context context, int textViewResourceId, List<Article> article){
        super(context,textViewResourceId, article);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article as = getItem(position);
        View view;
        ViewHolder viewHolder;
        //通常convertView中会已经有这些资源注册了
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = view.findViewById(R.id.sa_title);
            viewHolder.username = view.findViewById(R.id.sa_username);
            viewHolder.sContent = view.findViewById(R.id.sa_content);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(as.getTitle());
        viewHolder.username.setText(Integer.toString(as.getUserId()));
        return view;
    }
*/
    /* 未优化代码
    //?
    Article as = getItem(position);
    View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
    //注册组件
    TextView asTitle = view.findViewById(R.id.sa_title);
    TextView asUsername = view.findViewById(R.id.sa_username);
    TextView asContent = view.findViewById(R.id.sa_content);
    //设置内容
        asTitle.setText(as.getTitle());
        asUsername.setText(as.getUser().getUsername());
        asContent.setText(as.getSimpleA());*/
}
