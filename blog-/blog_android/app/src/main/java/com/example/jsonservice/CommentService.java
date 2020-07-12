package com.example.jsonservice;

import com.example.bean.Comment;
import com.example.bean.UserDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//{"comments":[{"blogId":0,"comment":"qttff","userId":0,"userNickName":"弗兰妮"},{"blogId":0,"comment":"qttffgsgrgesgr","userId":0,"userNickName":"弗兰妮"},{"blogId":0,"comment":"fdasjfpsiadjfsidjfpasdjf","userId":0,"userNickName":"弗兰妮"}]}


public class CommentService {
    public static Comment getComment(JSONObject jsonObject){
        Comment comment = new Comment();
        try {
            comment.setUserNickName(jsonObject.getString("userNickName"));
            comment.setComment(jsonObject.getString("comment"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return comment;
    }

    public static List<Comment> getComments(String jsonString){
        List<Comment> list = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("comments");
            for (int i = 0; i < jsonArray.length(); i++) {
                //生成JsonObject的一个单一对象 对应 ArticleDetail
                // 包含  Article  UserDetail
                JSONObject commentObject = jsonArray.getJSONObject(i);
                Comment comment = getComment(commentObject);
                if(comment != null){
                    list.add(comment);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
