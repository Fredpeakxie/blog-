package com.example.bean;

/**
 * list中的bean元素 代表数据模型 届时需要从 数据库获取数据
 * 现有的基本要素
 *      1空参构造器
 *      2全参构造器
 *      3get and set方法  个人感觉 set方法可去掉
 */
public class User {
    private int userId;
    private String username;
    private String email;
    private int icon;//后期可用于图像 具体机制现不明了



    public User() {
    }

    public User(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User(String username, String email, int icon) {
        this.username = username;
        this.email = email;
        this.icon = icon;
    }
}
