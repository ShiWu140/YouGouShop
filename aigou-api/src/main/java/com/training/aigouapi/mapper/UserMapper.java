package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface UserMapper {
    /**
     * 查询用户分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 用户列表
     */
    List<User> selectLimit(int start, int limit);

    /**
     * 查询用户总数
     *
     * @return 用户总数
     */
    long selectCount();

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> selectAll();

    /**
     * 按id查询用户
     *
     * @param userId 用户ID
     * @return 用户对象，如果不存在则返回null
     */
    User selectId(String userId);

    /**
     * 按用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象，如果不存在则返回null
     */
    User selectByName(String userName);

    /**
     * 插入新用户
     *
     * @param user 用户对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(User user);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String userId);

    /**
     * 更新用户
     *
     * @param user 用户对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(User user);
}
