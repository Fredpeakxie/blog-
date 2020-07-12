package com.fred.dao.impl;

import com.fred.bean.User;
import com.fred.bean.UserDetail;
import com.fred.dao.BaseDAO;
import com.fred.dao.UserDetailDAO;

/**
 * @auther fred
 * @create 2020-05-29 10:18
 */
public class UserDetailDaoImpl extends BaseDAO implements UserDetailDAO {

    @Override
    public UserDetail queryUserDetailByUserId(Integer userId) {
        String sql = "select `user_id` userId ,`nickname`,`introduction`,`blog_num` blogNum ,`getlike_num` getLikeNum, getmark_num getMarkNum " +
                "from t_user_detail " +
                "where user_id = ? ;";
        return queryForOne(UserDetail.class,sql,userId);
    }

    @Override
    public boolean updateUserDetailStr(int userId,String nickname,String introduction) {
        String sql = "UPDATE t_user_detail\n" +
                "SET nickname = ?,\n" +
                "introduction = ?\n" +
                "WHERE user_id = ?;";
        int i = commonIUD(sql, nickname, introduction, userId);
        if(i == -1){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public int insertUserDetail(int userId, String nickname, String introduction) {
        String sql = "INSERT INTO t_user_detail(`user_id`,`nickname`,`introduction`) " +
                "VALUES(?,?,?);";
        return commonIUD(sql,userId,nickname,introduction);
    }


    @Override
    public int userArticleNumAdd(int userId) {
        String sql = "UPDATE t_user_detail\n" +
                "SET blog_num = blog_num+1\n" +
                "WHERE user_id = ?;";
        return commonIUD(sql,userId);
    }

    @Override
    public int userLikeNumAdd(int userId) {
        String sql = "UPDATE t_user_detail\n" +
                "SET getLike_num = getLike_num+1\n" +
                "WHERE user_id = ?;";
        return commonIUD(sql,userId);
    }

    @Override
    public int userMarkNumAdd(int userId) {
        String sql = "UPDATE t_user_detail\n" +
                "SET getMark_num = getMark_num+1\n" +
                "WHERE user_id = ?;";
        return commonIUD(sql,userId);
    }

    @Override
    public int userArticleNumDec(int userId) {
        String sql = "UPDATE t_user_detail\n" +
                "SET blog_num = blog_num-1\n" +
                "WHERE user_id = ?;";
        return commonIUD(sql,userId);
    }

    @Override
    public int userLikeNumDec(int userId) {
        String sql = "UPDATE t_user_detail\n" +
                "SET getLike_num = getLike_num-1\n" +
                "WHERE user_id = ?;";
        return commonIUD(sql,userId);
    }

    @Override
    public int userMarkNumDec(int userId) {
        String sql = "UPDATE t_user_detail\n" +
                "SET getMark_num = getMark_num-1\n" +
                "WHERE user_id = ?;";
        return commonIUD(sql,userId);
    }
}
