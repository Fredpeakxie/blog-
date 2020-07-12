package com.fred.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库的通用操作
 * @auther fred
 * @create 2020-05-21 7:08
 */
public class JDBCUtils {
    private static DataSource dataSource;
    static {
        Properties properties = null;
        try {
            properties = new Properties();
//            InputStream inputStream = new FileInputStream("jdbc.properties");
            InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return null if getConnection failed else return a connection
     */
    public static Connection getConnection(){
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**关闭连接只是把连接归还到连接池中
     * close connection resource only return back the connection to pool
     * @param connection
     */
    public static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        Connection connection = getConnection();
//        System.out.println(connection);
//        closeConnection(connection);
//    }
}
