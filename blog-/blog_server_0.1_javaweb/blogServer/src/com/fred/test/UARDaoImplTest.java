package com.fred.test;

import com.fred.bean.UserArticleRelation;
import com.fred.dao.UARDAO;
import com.fred.dao.impl.UARDaoImpl;

/**
 * @auther fred
 * @create 2020-06-10 8:12
 */
class UARDaoImplTest {
    UARDAO uarDao = new UARDaoImpl();
    void setUp() {
    }

    void setArticleLike() {
//        uarDao.setArticleLike(new UserArticleRelation(1,2));
        for (int i = 4; i < 20; i++) {
            uarDao.setArticleLike(new UserArticleRelation(2,i));
        }
    }

    void setArticleMark() {
        for (int i = 4; i < 20; i++) {
        uarDao.setArticleMark(new UserArticleRelation(2,i));
        }
    }

    void getArticleLikeByUser() {
        uarDao.getArticleLikeByUser(2).forEach(System.out::println);
    }

    void getArticleMarkByUser() {
        uarDao.getArticleMarkByUser(2).forEach(System.out::println);
    }
}