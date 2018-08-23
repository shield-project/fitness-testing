package org.alittlebitch.fitness.tcm.service;

import org.alittlebitch.fitness.dto.LoginUser;
import org.alittlebitch.fitness.tcm.bean.User;
import org.alittlebitch.fitness.tcm.dao.UserDao;
import org.shoper.commons.core.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author ShawnShoper
 * @date 2018/8/23 10:41
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public LoginUser login(String username, String password) {
        if (StringUtil.isAnyEmpty(username, password))
            throw new IllegalArgumentException("用户名密码不能为空");
        User user = userDao.selectUserCount(username, password);
        if (Objects.isNull(user))
            throw new RuntimeException("用户名密码错误");
        String token = UserToken.login(user);
        LoginUser loginUser = new LoginUser();
        loginUser.setToken(token);
        loginUser.setUser(user);
        return loginUser;

    }

    public void changePwd(String username, String password) {
        if (StringUtil.isAnyEmpty(username, password))
            throw new IllegalArgumentException("用户名密码不能为空");
        if (userDao.changePwd(username, password) == 0)
            throw new RuntimeException("修改密码失败");
    }

    public void addUser(String username, String password, String name) {
        if (StringUtil.isAnyEmpty(username, password, name))
            throw new IllegalArgumentException("用户名，密码,名字不能为空");
        if (userDao.existsUser(username) != 0)
            throw new RuntimeException("用户已存在");
        else
            userDao.addUser(username, password, name);
    }

    public List<User> users(String userName, int page, int pageSize) {
        if (page == 0)
            page = 1;
        if (pageSize == 0 || pageSize > 50)
            pageSize = 10;
        StringBuilder sql = new StringBuilder();
        sql.append("select username,name from testing_user ");
        if (!StringUtil.isEmpty(userName))
            sql.append("where username like '%" + userName + "%' ");
        sql.append("limit " + (page - 1) * pageSize + "," + pageSize);

        List<User> list = userDao.list(sql.toString());
        return list;
    }
}
