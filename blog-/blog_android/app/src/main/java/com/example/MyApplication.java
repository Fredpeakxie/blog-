package com.example;

import android.app.Application;

import com.example.bean.User;
import com.example.bean.UserDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Application 类用于存储全局变量
 * 全局变量
 *  user 用户id
 *  isLogin 登录状态
 */
public class MyApplication extends Application {
    private User user = new User();
    private UserDetail userDetail = new UserDetail();
    private boolean isLogin = false;
    public List<Integer> likeArticles = new ArrayList<>();
    public List<Integer> markArticles = new ArrayList<>();

    /**
     * 退出登录时调用
     */
    public void exit(){
        user = new User();
        userDetail = new UserDetail();
        isLogin = false;
        likeArticles.clear();
        markArticles.clear();
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public User getUser() {
        return user;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }
}
