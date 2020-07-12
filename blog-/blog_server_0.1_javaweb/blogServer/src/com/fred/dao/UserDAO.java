package com.fred.dao;

import com.fred.bean.User;

/**
 * 未来可能还有 用户密码更换和找回
 * @auther fred
 * @create 2020-05-21 7:46
 */
public interface UserDAO {
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
