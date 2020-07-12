package com.fred.service.impl;

import com.fred.bean.User;
import com.fred.bean.UserArticleRelation;
import com.fred.bean.UserDetail;
import com.fred.dao.ArticleDAO;
import com.fred.dao.UARDAO;
import com.fred.dao.UserDAO;
import com.fred.dao.UserDetailDAO;
import com.fred.dao.impl.ArticleDaoImpl;
import com.fred.dao.impl.UARDaoImpl;
import com.fred.dao.impl.UserDaoImpl;
import com.fred.dao.impl.UserDetailDaoImpl;
import com.fred.json.JsonTools;
import com.fred.service.UserService;

import java.util.List;

/**
 * @auther fred
 * @create 2020-05-21 7:56
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDao = new UserDaoImpl();
    private UserDetailDAO userDetailDao = new UserDetailDaoImpl();
    private UARDAO uarDao = new UARDaoImpl();
    private ArticleDAO articleDAO = new ArticleDaoImpl();

    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
        //需要获取当前User的id
        int userId = userDao.getRegisterUserId();
        userDetailDao.insertUserDetail(userId,"default","该用户很懒，没有介绍");
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existUsername(String username) {
        if(userDao.queryUserByUsername(username) == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public UserDetail findUserDetailById(Integer userId) {
        return userDetailDao.queryUserDetailByUserId(userId);
    }

    @Override
    public boolean updateUserDetailStr(int userId, String nickname, String introduction) {
        userDetailDao.updateUserDetailStr(userId,nickname,introduction);
        return false;
    }

}
