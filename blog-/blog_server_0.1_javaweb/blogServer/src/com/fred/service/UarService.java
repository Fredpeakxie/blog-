package com.fred.service;

import com.fred.bean.UserArticleRelation;

/**
 * @auther fred
 * @create 2020-06-13 6:36
 */
public interface UarService {

    /**
     * 用户阅读 被读文章 阅读量+1
     * @param blogId
     */
    public void readAdd(int blogId);

    /**
     * 用户点赞 被读文章 点赞量+1
     * @param blogId
     */
    public void likeAdd(int userId,int blogId);

    /**
     * 用户收藏 被读文章 收藏量+1
     * @param blogId
     */
    public void markAdd(int userId,int blogId);

    public String getLikes(int userId);

    public String getMarks(int userId);

    public void removeLike(UserArticleRelation uar);

    public void removeMark(UserArticleRelation uar);
}
