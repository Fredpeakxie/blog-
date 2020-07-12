package com.example.jsonservice;

import com.example.bean.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {
    //{"article":[{"blogId":19,"likeNum":20,"markNum":0,"readNum":19,"title":"fred18","userId":1},
    // {"blogId":18,"likeNum":19,"markNum":0,"readNum":18,"title":"fred17","userId":1},
    // {"blogId":17,"likeNum":18,"markNum":0,"readNum":17,"title":"fred16","userId":1},
    // {"blogId":16,"likeNum":17,"markNum":0,"readNum":16,"title":"fred15","userId":1},
    // {"blogId":15,"likeNum":16,"markNum":0,"readNum":15,"title":"fred14","userId":1},
    // {"blogId":14,"likeNum":15,"markNum":0,"readNum":14,"title":"fred13","userId":1},
    // {"blogId":13,"likeNum":14,"markNum":0,"readNum":13,"title":"fred12","userId":1},
    // {"blogId":12,"likeNum":13,"markNum":0,"readNum":12,"title":"fred11","userId":1},
    // {"blogId":11,"likeNum":12,"markNum":0,"readNum":11,"title":"fred10","userId":1},
    // {"blogId":10,"likeNum":11,"markNum":0,"readNum":10,"title":"fred9","userId":1}]}

    //{"blogId":19,"likeNum":20,"markNum":0,"readNum":19,"title":"fred18","userId":1}
    public static Article getArticle(String jsonString){
        Article as = new Article();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject articleSimpleObject = jsonObject.getJSONObject("article");//?key
            as.setBlogId(articleSimpleObject.getInt("blogId"));
            as.setUserId(articleSimpleObject.getInt("userId"));
            as.setTitle(articleSimpleObject.getString("title"));
            as.setReadNum(articleSimpleObject.getInt("readNum"));
            as.setLikeNum(articleSimpleObject.getInt("likeNum"));
            as.setMarkNum(articleSimpleObject.getInt("markNum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return as;
    }

    public static Article getArticle(JSONObject jsonObject){
        Article as = new Article();
        try {
            as.setBlogId(jsonObject.getInt("blogId"));
            as.setUserId(jsonObject.getInt("userId"));
            as.setTitle(jsonObject.getString("title"));
            as.setReadNum(jsonObject.getInt("readNum"));
            as.setLikeNum(jsonObject.getInt("likeNum"));
            as.setMarkNum(jsonObject.getInt("markNum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return as;
    }

    public static List<Article> getArticles(String key, String jsonString){
        List<Article> list = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject articleSimpleObject = jsonArray.getJSONObject(i);
                Article as = new Article();
                as.setBlogId(articleSimpleObject.getInt("blogId"));
                as.setUserId(articleSimpleObject.getInt("userId"));
                as.setTitle(articleSimpleObject.getString("title"));
                as.setReadNum(articleSimpleObject.getInt("readNum"));
                as.setLikeNum(articleSimpleObject.getInt("likeNum"));
                as.setMarkNum(articleSimpleObject.getInt("markNum"));
                list.add(as);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
