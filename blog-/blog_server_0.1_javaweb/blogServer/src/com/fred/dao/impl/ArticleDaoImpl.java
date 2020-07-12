package com.fred.dao.impl;

import com.fred.bean.Article;
import com.fred.dao.ArticleDAO;
import com.fred.dao.BaseDAO;

import java.util.List;

/**
 * @auther fred
 * @create 2020-05-28 8:53
 */
public class ArticleDaoImpl extends BaseDAO implements ArticleDAO {

    @Override
    public int saveArticle(Article article) {
        String sql = "insert into t_article(`user_id`,`title`) " +
                "values(?,?);";
        return commonIUD(sql,article.getUserId(),article.getTitle());
    }

    @Override
    public int articleReadAdd(Integer blog_id) {
        String sql = "update t_article " +
                "    set read_num = read_num+1 " +
                "    where blog_id = ?;";
        return commonIUD(sql,blog_id);
    }

    @Override
    public int articleLikeAdd(Integer blog_id) {
        String sql = "update t_article " +
                "    set like_num = like_num+1 " +
                "    where blog_id = ?;";
        return commonIUD(sql,blog_id);
    }

    @Override
    public int articleMarkAdd(Integer blog_id) {
        String sql = "update t_article " +
                "    set mark_num = mark_num+1 " +
                "    where blog_id = ?;";
        return commonIUD(sql,blog_id);
    }

    @Override
    public int articleLikeDec(Integer blog_id) {
        String sql = "update t_article " +
                "    set like_num = like_num-1 " +
                "    where blog_id = ?;";
        return commonIUD(sql,blog_id);
    }

    @Override
    public int articleMarkDec(Integer blog_id) {
        String sql = "update t_article " +
                "    set mark_num = mark_num-1 " +
                "    where blog_id = ?;";
        return commonIUD(sql,blog_id);
    }



    @Override
    public List<Article> getArticleList(int startNum,int articleNum) {
        String sql = "SELECT blog_id blogId,user_id userId,title," +
                "like_num likeNum,read_num readNum,mark_num markNum " +
                "FROM t_article " +
                "ORDER BY read_num + like_num*4 + mark_num*10 + blog_id*0.1 DESC " +
                "LIMIT ?,?;";
        return queryForList(Article.class,sql,startNum,articleNum);
    }

    @Override
    public List<Article> getMarkedArticleList(int userId) {
        String sql = "SELECT blog_id blogId,user_id userId,title, " +
                "like_num likeNum,read_num readNum,mark_num markNum " +
                "FROM t_article\n" +
                "WHERE blog_id IN(\n" +
                "SELECT blog_id\n" +
                "FROM t_mark\n" +
                "WHERE user_id = ?) ;";
        return queryForList(Article.class,sql,userId);
    }

    @Override
    public List<Article> getMyArticleList(int userId) {
        String sql = "SELECT blog_id blogId,user_id userId,title, " +
                "like_num likeNum,read_num readNum,mark_num markNum " +
                "FROM t_article\n" +
                "WHERE user_id = ?;";
        return queryForList(Article.class,sql,userId);
    }

    @Override
    public int getArticleNum() {
        String sql = "SELECT MAX(blog_id)\n" +
                "FROM t_article;";
        int i = (Integer) queryForSingValue(sql);
        return i;
    }

    @Override
    public int getArticleId(int userId, String title) {
        String sql = "SELECT MAX(blog_id)\n" +
                "FROM t_article\n" +
                "WHERE user_id = ? AND title = ?;";
        Long l = (Long) queryForSingValue(sql,userId,title);
        return l.intValue();
    }

    @Override
    public int getAuthorId(int blogId){
        String sql = "SELECT user_id \n" +
                "FROM t_article \n" +
                "WHERE blog_id = ?; ";
        int i = (Integer) queryForSingValue(sql,blogId);
        return i;
    }


//    String sql = "select 'blog_id','user_id','title' " +
//            "from t_article " +
//            "where "
}
