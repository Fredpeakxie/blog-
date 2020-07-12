package com.fred.dao;

import com.fred.bean.Article;

import java.util.List;

/**
 * @auther fred
 * @create 2020-05-28 8:46
 * 后期可对用户读过的文章 点赞 收藏的 文章 无法反复操作 记录在用户上
 */
public interface ArticleDAO {
    /**
     * 用户（作者） 创建 文章
     * @param article
     * @return
     */
    public int saveArticle(Article article);

    /**
     * （用户）读者 阅读时 固定增加1
     * @param blog_id
     * @return
     */
    public int articleReadAdd(Integer blog_id);

    /**
     * （用户）读者 双击喜欢 固定增加1
     * @param blog_id
     * @return
     */
    public int articleLikeAdd(Integer blog_id);

    /**
     * （用户）读者 收藏时 固定增加1
     * @param blog_id
     * @return
     */
    public int articleMarkAdd(Integer blog_id);


    /**
     * （用户）读者 双击喜欢 固定增加1
     * @param blog_id
     * @return
     */
    public int articleLikeDec(Integer blog_id);

    /**
     * （用户）读者 收藏时 固定增加1
     * @param blog_id
     * @return
     */
    public int articleMarkDec(Integer blog_id);

    /**
     * 获取指定个数的 文章 获取方式 使用收藏*10 点赞*4 阅读*1 的排名
     * @param startNum 文章 开始索引
     * @param articleNum 指定的文章个数
     * @return
     */
    public List<Article> getArticleList(int startNum,int articleNum);

    public List<Article> getMarkedArticleList(int userId);

    public List<Article> getMyArticleList(int userId);

    /**
     * 获取现有的文章数量
     * @return
     */
    public int getArticleNum();

    /**
     * 获取以下限制条件的 ArticleID 其中使用降序法 找到Id号最大的
     * @param userId
     * @param title
     * @return
     */
    public int getArticleId(int userId,String title);

    public int getAuthorId(int blogId);
}
