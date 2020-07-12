package com.fred.service.impl;

import com.fred.bean.Article;
import com.fred.bean.UserArticleRelation;
import com.fred.dao.ArticleDAO;
import com.fred.dao.UARDAO;
import com.fred.dao.UserDetailDAO;
import com.fred.dao.impl.ArticleDaoImpl;
import com.fred.dao.impl.UARDaoImpl;
import com.fred.dao.impl.UserDetailDaoImpl;
import com.fred.service.ArticleService;

import java.util.List;

/**
 * @auther fred
 * @create 2020-05-28 21:06
 */
public class ArticleServiceImpl implements ArticleService {
    private ArticleDAO articleDao = new ArticleDaoImpl();
    private UserDetailDAO userDetailDAO = new UserDetailDaoImpl();



    @Override
    public List<Article> getArticles(int index) {
        return articleDao.getArticleList(index,10);
    }

    @Override
    public List<Article> getMarkedArticles(int userId) {
        return articleDao.getMarkedArticleList(userId);
    }

    @Override
    public List<Article> getMyArticles(int userId) {
        return articleDao.getMyArticleList(userId);
    }

    @Override
    public int addArticle(int userId, String title){
        int i = articleDao.saveArticle(new Article(userId, title));
        if(i == -1){
            return -1;
        }else {
            //做并发操作一定会出问题  可以定期删除同用户 同标题的 文章
            int articleNum = articleDao.getArticleNum();
            //作者的文章数增加
            userDetailDAO.userArticleNumAdd(userId);
            return articleNum;
        }
    }
}
