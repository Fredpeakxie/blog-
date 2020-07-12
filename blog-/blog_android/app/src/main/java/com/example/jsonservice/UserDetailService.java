package com.example.jsonservice;

import com.example.bean.Article;
import com.example.bean.UserDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class UserDetailService {
//    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}
    public static UserDetail getUserDetail(String jsonString){
        UserDetail ud = new UserDetail();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject userDetailObject = jsonObject.getJSONObject("userDetail");
            ud.setUserId(userDetailObject.getInt("userId"));
            ud.setIntroduction(userDetailObject.getString("introduction"));
            ud.setNickname(userDetailObject.getString("nickname"));
            ud.setBlogNum(userDetailObject.getInt("blogNum"));
            ud.setGetLikeNum(userDetailObject.getInt("getLikeNum"));
            ud.setGetMarkNum(userDetailObject.getInt("getMarkNum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ud;
    }

//    public static UserDetail getUserDetail(JSONObject userDetailJ){
//        UserDetail ud = new UserDetail();
//        try {
//            ud.setBlogNum(userDetailJ.getInt("blogNum"));
//            ud.setUserId(userDetailJ.getInt("userId"));
//            ud.setNickname(userDetailJ.getString("nickname"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return ud;
//    }

    public static UserDetail getUserDetail(JSONObject userDetailJ){
        UserDetail ud = new UserDetail();
        try {
            ud.setUserId(userDetailJ.getInt("userId"));
            ud.setNickname(userDetailJ.getString("nickname"));
            ud.setIntroduction(userDetailJ.getString("introduction"));
            ud.setBlogNum(userDetailJ.getInt("blogNum"));
            ud.setGetLikeNum(userDetailJ.getInt("getLikeNum"));
            ud.setGetMarkNum(userDetailJ.getInt("getMarkNum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ud;
    }

    public static List<Integer> getUserLike(String jsonString ){
        List<Integer> likes = new ArrayList<Integer>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonLikes = jsonObject.getJSONArray("likes");
            for (int i = 0; i < jsonLikes.length(); i++) {
                int likeArticle = jsonLikes.getInt(i);
                likes.add(likeArticle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return likes;
    }

    public static List<Integer> getUserMark(String jsonString ){
        List<Integer> marks = new ArrayList<Integer>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonMarks = jsonObject.getJSONArray("marks");
            for (int i = 0; i < jsonMarks.length(); i++) {
                int markArticle = jsonMarks.getInt(i);
                marks.add(markArticle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return marks;
    }
}
