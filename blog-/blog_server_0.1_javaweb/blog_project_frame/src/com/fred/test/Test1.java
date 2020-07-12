package com.fred.test;

import com.fred.bean.Employee;
import com.fred.bean.User;
import com.fred.dao.EmployeeMapper;
import com.fred.dao.EmployeeMapperAnnotation;
import com.fred.dao.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @auther fred
 * @create 2020-06-14 7:22
 *
 * Dao    ===》 DaoImpl
 * Mapper ===》 xxMapper
 *
 * SqlSession 代表和数据库的一次会话
 * SqlSession 底层和connection一样 非线程安全
 * 每次使用都用改获取新的对象
 *  mapper没有是西安类 mybatis 会为给接口生成 一个代理对象
 *  通过接口和xml文件进行绑定实现
 *
 * 重要配置文件
 *      mybatis全局配置文件 mybatis-config.xml
 *      sql映射文件 保存了每一个sql语句的映射信息
 */
public class Test1 {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "conf/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * 1根据xml配置文件（全局配置文件）创建SqlSessionFactory对象
     * 2sql映射文件配置了每一个sql，以及SQL的封装规则
     * 3将sql映射文件注册在全局配置文件中
     * 4代码
     *  （1）根据全局配置文件得到SqlSessionFactory
     *  （2）使用sqlSessionFactory 获取sqlSession对象执行增删改差
     *          用完关闭
     *  （3）使用sql的唯一标志来告诉Mybatis执行那个sql
     * @throws IOException
     */
    @Test
    public void test() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取sqlSession实例
        //statement 唯一标识符
        //执行语句的 参数
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Employee employee = sqlSession.selectOne("com.fred.dao.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);
        } finally {
            //关闭会话
            sqlSession.close();
        }
    }

    @Test
    public void test01() throws IOException {
        //1获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2获取sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            //3获取接口的实现类
            //会为接口自动创建一个代理对象代理对象执行增删改查
            //以后要是使用其他方式（如jdbc，DBUtil） 就只需要更改实现类就行
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.getEmpById(1);
            System.out.println(empById);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void test02() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
            Employee empById = mapper.getEmpById(1);
            System.out.println(empById);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test03() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.queryUserByUsername("admin07");
            System.out.println(user);
        } finally {
            sqlSession.close();
        }
    }

    public static void main(String[] args) {
        String resource = "conf\\mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //获取sqlSession实例
        //statement 唯一标识符
        //执行语句的 参数
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Employee employee = sqlSession.selectOne("com.fred.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);
        } finally {
            //关闭会话
            sqlSession.close();
        }
    }
}
