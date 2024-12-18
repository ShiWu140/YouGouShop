package com.training.aigouapi.service;

import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.entity.User;

import java.util.List;

/**
 * 用户表业务逻辑接口
 *
 * @author ethan
 */
public interface UserService {
    /**
     * 分页查询用户信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<User> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有用户信息
     *
     * @return 用户信息列表
     */
    List<User> findAll();

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    User findId(String userId);

    /**
     * 登录业务逻辑
     *
     * @param userName 用户名
     * @param password 密码
     * @return 用户对象，登录成功返回用户对象，否则返回null
     */
    User login(String userName, String password);

    /**
     * 插入新的用户信息
     *
     * @param user 用户对象
     * @return 是否插入成功，true表示成功，false表示失败
     */
    boolean save(User user);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String userId);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(User user);
}
