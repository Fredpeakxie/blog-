package com.fred.test;

import com.fred.service.CommentService;
import com.fred.service.impl.CommentServiceImpl;

/**
 * @auther fred
 * @create 2020-06-12 11:11
 */
class CommentServiceTest {
    private CommentService commentService = new CommentServiceImpl();

    void publishComment() {
    }


    void getCommentsJson() {
        System.out.println(commentService.getCommentsJson(18));
    }
}