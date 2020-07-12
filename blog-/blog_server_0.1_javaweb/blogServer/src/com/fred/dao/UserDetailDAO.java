package com.fred.dao;

import com.fred.bean.User;
import com.fred.bean.UserDetail;

/**
 * @auther fred
 * @create 2020-05-29 10:17
 */
public interface UserDetailDAO {
    /**
     * 通过用户Id查找用户
     * @param userId
     * @return
     */
    public UserDetail queryUserDetailByUserId(Integer userId);

    public boolean updateUserDetailStr(int userId,String nickname,String introduction);

    public int insertUserDetail(int userId,String nickname,String introduction);

    public int userArticleNumAdd(int userId);

    public int userLikeNumAdd(int userId);

    public int userMarkNumAdd(int userId);

    public int userArticleNumDec(int userId);

    public int userLikeNumDec(int userId);

    public int userMarkNumDec(int userId);
}
