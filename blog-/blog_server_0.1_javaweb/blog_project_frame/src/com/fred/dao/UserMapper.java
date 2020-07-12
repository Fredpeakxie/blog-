package com.fred.dao;

import com.fred.bean.User;

/**
 * @auther fred
 * @create 2020-06-15 11:17
 */
public interface UserMapper {
    /**
     * 通过用户名查询用户
     * 主要防止用户名重复
     * @param username
     * @return
     */
    public User queryUserByUsername(String username);

    /**
     * 查询用户名和密码相符的User
     * 返回空 在数据库中找不到 对应的用户名和密码
     * @param username
     * @param password
     * @return
     */
    public User queryUserByUsernameAndPassword(String username,String password);

    /**
     * 保存用户
     * @param user
     * @return
     */
    public int saveUser(User user);

    /**
     * 获得最后注册的 UserId
     * @return
     */
    public int getRegisterUserId();
}
