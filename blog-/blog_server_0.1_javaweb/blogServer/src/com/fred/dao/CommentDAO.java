package com.fred.dao;

import com.fred.bean.Comment;

import java.util.List;

/**
 * @auther fred
 * @create 2020-06-12 8:40
 */
public interface CommentDAO {

    public boolean insertComment(Comment comment);

    public List<Comment> queryCommentsByBlogId(int blogId);

}
