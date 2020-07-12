package com.fred.dao;

import com.alibaba.druid.util.JdbcUtils;
import com.fred.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 考虑是否要更换方法类型为protected
 * 当前类下的所有操作都是单条操作不涉及一致性问题
 * 若有一致性问题 请建立新类 BaseMultiDAO
 * @auther fred
 * @create 2020-05-21 7:23
 */
public abstract class BaseDAO {
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * 通用的操作 Insert Update Delete
     * @param sql
     * @param args
     * @return -1 if 操作失败
     */
    public int commonIUD(String sql,Object... args){
        Connection connection = JDBCUtils.getConnection();
        try {
            return queryRunner.update(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(Arrays.toString(args) + "插入失败");
        }finally {
            JDBCUtils.closeConnection(connection);
        }
        return -1;
    }

    /**
     * 单条记录的查询
     * @param type 查询Bean类型
     * @param sql
     * @param args
     * @param <T>
     * @return null 查询操作出错
     */
    public <T> T queryForOne(Class<T> type,String sql,Object... args){
        Connection connection = JDBCUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(connection);
        }
        return null;
    }

    /**
     *
     * @param type 查询Bean类型
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(Class<T> type, String sql, Object... args){
        Connection connection = JDBCUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(connection);
        }
        return null;
    }

    /**
     *  一些通用查询操作 比如查询用户数量..
     * @param sql
     * @param args
     * @return
     */
    public Object queryForSingValue(String sql,Object... args){
        Connection connection = JDBCUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new ScalarHandler(),args);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(connection);
        }
        return null;
    }
}
