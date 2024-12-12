package com.training.aigouapi.service.impl;


import com.training.aigouapi.dao.UserDAO;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.User;
import com.training.aigouapi.service.UserService;
import com.training.aigouapi.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务逻辑实现类
 *
 * @author ethan
 */
@Service
public class UserServiceImpl implements UserService {

    //调用dao层对象
    @Resource
    private UserDAO userDAO;

    /**
     * 查询分页方法
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return
     */
    @Override
    public PageEntity<User> findPage(Integer current, Integer pageSize) {
        //查询总数
        long total = userDAO.selectCount();
        //分页查询数据
        List<User> users = userDAO.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<User> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(total);
        pageEntity.setRecords(users);
        return pageEntity;
    }

    @Override
    public List<User> findAll() {
        return userDAO.selectAll();
    }

    @Override
    public User findId(String userId) {
        return userDAO.selectId(userId);
    }

    @Override
    public User login(String userName, String password) {
        User user = userDAO.selectByName(userName);
        if (user != null && MD5Utils.md5(password).equals(user.getUserPwd())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public boolean save(User user) {
        return userDAO.insert(user);
    }

    @Override
    public boolean remove(String userId) {
        return userDAO.delete(userId);
    }

    @Override
    public boolean update(User user) {
        return userDAO.update(user);
    }
}
