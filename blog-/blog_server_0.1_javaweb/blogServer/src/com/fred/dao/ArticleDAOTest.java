package com.fred.dao;

import com.fred.dao.impl.ArticleDaoImpl;
import org.junit.Test;

/**
 * @auther fred
 * @create 2020-06-12 18:04
 */
class ArticleDAOTest {
    private ArticleDAO articleDAO = new ArticleDaoImpl();
    @Test
    void getAuthorId() {
        System.out.println(articleDAO.getAuthorId(63));
    }
}