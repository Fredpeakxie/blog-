package com.fred.service;

import com.fred.bean.User;
import com.fred.bean.UserArticleRelation;
import com.fred.bean.UserDetail;

import java.util.List;

/**
 * @auther fred
 * @create 2020-05-21 7:53
 */
public interface UserService {
    /**
     * 用户注册
     * @param user
     */
    public void registerUser(User user);

    /**
     * 用户登录
     * 返回空 当用户名或密码不正确时（在数据库中找不到 对应的用户名和密码）
     * @param user
     * @return null if username or password wrong
     */
    public User login(User user);

    /**
     * 查找用户名是否已存在
     * @param username
     * @return true 如果用户名已存在 false 用户名 通过
     */
    public boolean existUsername(String username);

    public UserDetail findUserDetailById(Integer UserId);

    /**
     * 完成对用户的 nickname 和 introduction 的更新
     * @param userId
     * @param nickname
     * @param introduction
     * @return
     */
    public boolean updateUserDetailStr(int userId,String nickname,String introduction);
}
