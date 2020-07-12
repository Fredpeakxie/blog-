package com.fred.dao;

import com.fred.bean.UserArticleRelation;

import java.util.List;

/**
 * @auther fred
 * @create 2020-06-10 7:33
 */
public interface UARDAO {
    /**
     * 操作失败返回 -1
     * @param uar
     * @return
     */
    public int setArticleLike(UserArticleRelation uar);
    /**
     * 操作失败返回 -1
     * @param uar
     * @return
     */
    public int setArticleMark(UserArticleRelation uar);

    int removeArticleLike(UserArticleRelation uar);

    int removeArticleMark(UserArticleRelation uar);

    public List<Integer> getArticleLikeByUser(Integer userId);

    public List<Integer> getArticleMarkByUser(Integer userId);
}
