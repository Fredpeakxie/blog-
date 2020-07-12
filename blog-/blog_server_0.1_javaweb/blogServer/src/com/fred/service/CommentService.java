package com.fred.service;

import com.fred.bean.Comment;

/**
 * @auther fred
 * @create 2020-06-12 8:48
 */
public interface CommentService {
    public Boolean publishComment(Comment comment);

    public String getCommentsJson(int blogId);
}
