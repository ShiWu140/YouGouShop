package com.training.aigouapi.dao.impl;


import com.training.aigouapi.dao.UserDAO;
import com.training.aigouapi.entity.User;
import com.training.aigouapi.util.JDBCUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据接口实现类
 *
 * @author ethan
 */
@Repository
public class UserDAOImpl implements UserDAO {
    /**
     * 查询分页方法
     *
     * @param start 起始页
     * @param limit 返回行数
     * @return
     */
    @Override
    public List<User> selectLimit(int start, int limit) {
        return JDBCUtils.executeQuery(User.class, "select * from s_user limit ?, ?", start, limit);
    }

    /**
     * 查询总数
     *
     * @return count
     */
    @Override
    public long selectCount() {
        Integer count = JDBCUtils.executeCount("select count(*) from s_user");
        return count;
    }

    @Override
    public List<User> selectAll() {
        return JDBCUtils.executeQuery(User.class, "select * from s_user");
    }

    @Override
    public User selectId(String userId) {
        List<User> users = JDBCUtils.executeQuery(User.class, "select * from s_user where user_id = ?", userId);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public boolean insert(User user) {
        int rows = JDBCUtils.executeUpdate("insert into s_user(user_id,user_name,user_pwd,user_type) values(?,?,?,?)",
                user.getUserId(), user.getUserName(), user.getUserPwd(), user.getUserType());
        return rows > 0;
    }

    @Override
    public boolean delete(String userId) {
        int rows = JDBCUtils.executeUpdate("delete from  s_user where user_id = ?", userId);
        return rows > 0;
    }

    @Override
    public boolean update(User user) {
        int rows = JDBCUtils.executeUpdate("update s_user set user_name=?,user_pwd=?,user_type=? where user_id =?",
                user.getUserName(), user.getUserPwd(), user.getUserType(), user.getUserId());
        return rows > 0;
    }

    @Override
    public User selectByName(String userName) {
        List<User> users = JDBCUtils.executeQuery(User.class, "select * from s_user where user_name = ?", userName);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
