package com.fred.dao.impl;

import com.fred.bean.Comment;
import com.fred.dao.BaseDAO;
import com.fred.dao.CommentDAO;

import java.util.List;

/**
 * @auther fred
 * @create 2020-06-12 8:41
 */
public class CommentDaoImpl extends BaseDAO implements CommentDAO {

    @Override
    public boolean insertComment(Comment comment) {
        String sql = "INSERT INTO t_comment(user_id,blog_id,`comment`)\n" +
                "VALUES(?,?,?);";
        int i = commonIUD(sql, comment.getUserId(), comment.getBlogId(), comment.getComment());
        if(i == -1){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public List<Comment> queryCommentsByBlogId(int blogId) {
        String sql = "SELECT ud.`nickname` userNickname , c.`comment` comment\n" +
                "FROM t_comment c\n" +
                "INNER JOIN t_user_detail ud\n" +
                "ON c.`user_id` = ud.user_id\n" +
                "WHERE blog_id = ? ;";
        return queryForList(Comment.class,sql,blogId);
    }


}
