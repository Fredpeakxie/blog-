package com.fred.service.impl;

import com.fred.bean.UserArticleRelation;
import com.fred.dao.ArticleDAO;
import com.fred.dao.UARDAO;
import com.fred.dao.UserDetailDAO;
import com.fred.dao.impl.ArticleDaoImpl;
import com.fred.dao.impl.UARDaoImpl;
import com.fred.dao.impl.UserDetailDaoImpl;
import com.fred.json.JsonTools;
import com.fred.service.UarService;

/**
 * @auther fred
 * @create 2020-06-13 6:36
 */
public class UarServiceImpl implements UarService {
    private ArticleDAO articleDao = new ArticleDaoImpl();
    private UARDAO uarDao = new UARDaoImpl();
    private UserDetailDAO userDetailDAO = new UserDetailDaoImpl();

    @Override
    public void readAdd(int blogId) {
        articleDao.articleReadAdd(blogId);
    }

    /**
     * 1 用户喜爱表中添加 该项
     * 2 文章被喜爱数量添加
     * 3 作者被喜爱数量添加
     * @param userId
     * @param blogId
     */
    @Override
    public void likeAdd(int userId,int blogId) {
        articleDao.articleLikeAdd(blogId);
        uarDao.setArticleLike(new UserArticleRelation(userId,blogId));
        //获取作者信息
        int authorId = articleDao.getAuthorId(blogId);
        userDetailDAO.userLikeNumAdd(authorId);
    }

    @Override
    public void markAdd(int userId,int blogId) {
        articleDao.articleMarkAdd(blogId);
        uarDao.setArticleMark(new UserArticleRelation(userId,blogId));
        //获取作者信息
        int authorId = articleDao.getAuthorId(blogId);
        userDetailDAO.userMarkNumAdd(authorId);
    }

    @Override
    public String getLikes(int userId) {
        String likes = JsonTools.createJsonString("likes", uarDao.getArticleLikeByUser(userId));
        return likes;
    }

    @Override
    public String getMarks(int userId) {
        String marks = JsonTools.createJsonString("marks", uarDao.getArticleMarkByUser(userId));
        return marks;
    }

    /**
     * 1 关系表中的移除
     * 2 文章中的移除
     * 3 作者中的移除
     * @param uar
     */
    @Override
    public void removeLike(UserArticleRelation uar){
        uarDao.removeArticleLike(uar);
        articleDao.articleLikeDec(uar.getBlogId());
        int authorId = articleDao.getAuthorId(uar.getBlogId());
        userDetailDAO.userLikeNumDec(authorId);
    }


    @Override
    public void removeMark(UserArticleRelation uar){
        uarDao.removeArticleMark(uar);
        articleDao.articleMarkDec(uar.getBlogId());
        int authorId = articleDao.getAuthorId(uar.getBlogId());
        userDetailDAO.userMarkNumDec(authorId);
    }
}
