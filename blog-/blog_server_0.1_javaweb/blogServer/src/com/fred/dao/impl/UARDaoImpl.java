package com.fred.dao.impl;

import com.fred.bean.UserArticleRelation;
import com.fred.dao.BaseDAO;
import com.fred.dao.UARDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther fred
 * @create 2020-06-10 7:35
 */
public class UARDaoImpl extends BaseDAO implements UARDAO {
    @Override
    public int setArticleLike(UserArticleRelation uar) {
        String sql = "insert into t_like(`user_id`,`blog_id`) " +
                "values(?,?);";
        return commonIUD(sql,uar.getUserId(),uar.getBlogId());
    }

    @Override
    public int setArticleMark(UserArticleRelation uar) {
        String sql = "insert into t_mark(`user_id`,`blog_id`) " +
                "values(?,?);";
        return commonIUD(sql,uar.getUserId(),uar.getBlogId());
    }

    @Override
    public int removeArticleLike(UserArticleRelation uar) {
        String sql = "delete from t_like " +
                " where `user_id` = ? and `blog_id` = ?";
        return commonIUD(sql,uar.getUserId(),uar.getBlogId());
    }

    @Override
    public int removeArticleMark(UserArticleRelation uar) {
        String sql = "delete from t_mark " +
                "where `user_id` = ? and `blog_id` = ?;";
        return commonIUD(sql,uar.getUserId(),uar.getBlogId());
    }

    @Override
    public List<Integer> getArticleLikeByUser(Integer userId) {
        String sql = "select `blog_id` blogId " +
                "from t_like " +
                "where user_id = ?;";
        List<UserArticleRelation> userArticleRelations = queryForList(UserArticleRelation.class, sql, userId);
        List<Integer> blogIds = new ArrayList<Integer>();
        for (UserArticleRelation uar : userArticleRelations) {
            blogIds.add(uar.getBlogId());
        }
        return blogIds;
    }

    @Override
    public List<Integer> getArticleMarkByUser(Integer userId) {
        String sql = "select `blog_id` blogId " +
                "from t_mark " +
                "where user_id = ?;";
        List<UserArticleRelation> userArticleRelations = queryForList(UserArticleRelation.class, sql, userId);
        List<Integer> blogIds = new ArrayList<Integer>();
        for (UserArticleRelation uar : userArticleRelations) {
            blogIds.add(uar.getBlogId());
        }
        return blogIds;
    }


}
