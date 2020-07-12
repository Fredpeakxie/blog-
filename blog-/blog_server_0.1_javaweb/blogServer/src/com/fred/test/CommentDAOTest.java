package com.fred.test;

import com.fred.bean.Comment;
import com.fred.dao.CommentDAO;
import com.fred.dao.impl.CommentDaoImpl;

/**
 * @auther fred
 * @create 2020-06-12 11:06
 */
class CommentDAOTest {
    private CommentDAO commentDAO = new CommentDaoImpl();

    void insertComment() {
    }


    void queryCommentsByBlogId() {
        commentDAO.queryCommentsByBlogId(18).forEach(System.out::println);
    }
}