package com.fred.service.impl;

import com.fred.bean.Comment;
import com.fred.dao.CommentDAO;
import com.fred.dao.impl.CommentDaoImpl;
import com.fred.json.JsonTools;
import com.fred.service.CommentService;

import java.util.List;

/**
 * @auther fred
 * @create 2020-06-12 8:49
 */
public class CommentServiceImpl implements CommentService {
    private CommentDAO commentDAO = new CommentDaoImpl();

    @Override
    public Boolean publishComment(Comment comment) {
        return commentDAO.insertComment(comment);
    }

    @Override
    public String getCommentsJson(int blogId) {
        List<Comment> comments = commentDAO.queryCommentsByBlogId(blogId);
        return JsonTools.createJsonString("comments",comments);
    }
}
