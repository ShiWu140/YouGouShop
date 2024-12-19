package com.training.aigoushopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.training.aigoushopapi.entity.User;
import com.training.aigoushopapi.entity.UserVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
public interface IUserService extends IService<User> {
    /**
     * 登录业务逻辑
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户对象，登录成功返回用户对象，否则返回null
     */
    UserVO login(String username, String password);

}
