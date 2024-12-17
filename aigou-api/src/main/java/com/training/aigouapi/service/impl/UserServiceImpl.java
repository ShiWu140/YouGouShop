package com.training.aigouapi.service.impl;


import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.User;
import com.training.aigouapi.mapper.UserMapper;
import com.training.aigouapi.service.UserService;
import com.training.aigouapi.util.IDUtils;
import com.training.aigouapi.util.MD5Utils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 业务逻辑实现类
 *
 * @author 十五
 */
@Service
public class UserServiceImpl implements UserService {

    //调用dao层对象
    @Resource
    private UserMapper userMapper;

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
        long total = userMapper.selectCount();
        //分页查询数据
        List<User> users = userMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<User> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(total);
        pageEntity.setRecords(users);
        return pageEntity;
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public User findId(String userId) {
        return userMapper.selectId(userId);
    }

    @Override
    public User login(String userName, String password) {
        User user = userMapper.selectByName(userName);
        if (user != null && MD5Utils.md5(password).equals(user.getUserPwd())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public boolean save(User user) {
        // 使用 ID 生成工具类生成新的 ID
        user.setUserId(IDUtils.get());
        // 使用 MD5Utils 生成工具类加密 ID
        user.setUserPwd(MD5Utils.md5(user.getUserPwd()));
        return userMapper.insert(user);
    }

    @Override
    public boolean remove(String userId) {
        return userMapper.delete(userId);
    }

    @Override
    public boolean update(User user) {
        return userMapper.update(user);
    }
}
