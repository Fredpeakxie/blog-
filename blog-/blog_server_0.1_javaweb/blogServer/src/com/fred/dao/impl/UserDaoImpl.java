package com.fred.dao.impl;

import com.fred.bean.User;
import com.fred.dao.BaseDAO;
import com.fred.dao.UserDAO;

/**
 * @auther fred
 * @create 2020-05-21 7:50
 */
public class UserDaoImpl extends BaseDAO implements UserDAO {
    @Override
    public User queryUserByUsername(String username) {
        String sql = "select `user_id`, `username`, `password`,`email` " +
                "from t_user " +
                "where username = ?;";
        return queryForOne(User.class,sql,username);
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        String sql = "select `user_id` userId, `username`, `password`,`email` " +
                "from t_user " +
                "where username = ? " +
                "and password = ? ;";
        return queryForOne(User.class,sql,username,password);
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into t_user(`username`,`password`,`email`) " +
                "values(?,?,?) ;";
        return commonIUD(sql,user.getUsername(),user.getPassword(),user.getEmail());
    }

    @Override
    public int getRegisterUserId() {
        String sql = "SELECT MAX(user_id)\n" +
                "FROM t_user ;";
        int i  = (Integer)queryForSingValue(sql);
        return i;
    }


}
