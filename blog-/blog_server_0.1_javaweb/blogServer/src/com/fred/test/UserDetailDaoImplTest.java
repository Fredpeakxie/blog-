package com.fred.test;

import com.fred.dao.UserDetailDAO;
import com.fred.dao.impl.UserDetailDaoImpl;

/**
 * @auther fred
 * @create 2020-06-12 17:47
 */
class UserDetailDaoImplTest {

    private UserDetailDAO userDetailDAO = new UserDetailDaoImpl();

    void queryUserDetailByUserId() {
    }

    void updateUserDetailStr() {
    }

    void insertUserDetail() {
    }

    void updateUserArticleNum() {
    }

    void updateUserLikeNum() {
        userDetailDAO.userLikeNumAdd(1);
    }

    void updateUserMarkNum() {
        userDetailDAO.userMarkNumAdd(1);
    }
}