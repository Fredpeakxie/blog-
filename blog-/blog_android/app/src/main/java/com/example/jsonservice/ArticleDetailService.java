package com.example.jsonservice;

import com.example.bean.Article;
import com.example.bean.ArticleDetail;
import com.example.bean.UserDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleDetailService {
    /*{"articleDetail":[
    {"article":{"blogId":19,"likeNum":20,"markNum":0,"readNum":19,"title":"fred18","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":18,"likeNum":19,"markNum":0,"readNum":18,"title":"fred17","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":17,"likeNum":18,"markNum":0,"readNum":17,"title":"fred16","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":16,"likeNum":17,"markNum":0,"readNum":16,"title":"fred15","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":15,"likeNum":16,"markNum":0,"readNum":15,"title":"fred14","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":14,"likeNum":15,"markNum":0,"readNum":14,"title":"fred13","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":13,"likeNum":14,"markNum":0,"readNum":13,"title":"fred12","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":12,"likeNum":13,"markNum":0,"readNum":12,"title":"fred11","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":11,"likeNum":12,"markNum":0,"readNum":11,"title":"fred10","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}},
    {"article":{"blogId":10,"likeNum":11,"markNum":0,"readNum":10,"title":"fred9","userId":1},
    "userDetail":{"blogNum":0,"nickname":"谢宇峰","userId":1}}]}
*/

    public static ArticleDetail getArticleDetail(String jsonString){
        ArticleDetail ad = new ArticleDetail();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject articleDetailObject = jsonObject.getJSONObject("articleDetail");//?key

            String articleJString = articleDetailObject.getString("article");
            articleDetailObject.getJSONObject("article");
            Article article = ArticleService.getArticle(articleJString);

            ad.setArticle(article);

            String userDetailJString = articleDetailObject.getString("userDetail");
            UserDetail userDetail = UserDetailService.getUserDetail( userDetailJString);
            ad.setUserDetail(userDetail);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ad;
    }

    public static List<ArticleDetail> getArticleDetails(String jsonString){
        List<ArticleDetail> list = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("articleDetails");
            for (int i = 0; i < jsonArray.length(); i++) {
                //生成JsonObject的一个单一对象 对应 ArticleDetail
                // 包含  Article  UserDetail
                JSONObject articleDetailObject = jsonArray.getJSONObject(i);
                ArticleDetail ad = new ArticleDetail();
                //获取 JsonObject 对应 Article （通过传入JsonObject）
                JSONObject articleJ = articleDetailObject.getJSONObject("article");
                Article article = ArticleService.getArticle(articleJ);
                ad.setArticle(article);
                //获取 JsonObject 对应 UserDetail （通过传入JsonObject）
                JSONObject userDetailJ = articleDetailObject.getJSONObject("userDetail");
                UserDetail userDetail = UserDetailService.getUserDetail(userDetailJ);
                ad.setUserDetail(userDetail);

                list.add(ad);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}

//                String userDetailJString = articleDetailObject.getString("userDetail");
//                UserDetail userDetail = UserDetailService.getUserDetail( userDetailJString);

//                String articleJString = articleDetailObject.getString("article");
//                Article article = ArticleService.getArticle(articleJString);