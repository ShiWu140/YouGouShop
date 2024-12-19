package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.entity.User;
import com.training.aigoushopapi.entity.UserVO;
import com.training.aigoushopapi.mapper.UserMapper;
import com.training.aigoushopapi.service.IUserService;
import com.training.aigoushopapi.util.JwtUtils;
import com.training.aigoushopapi.util.MD5Utils;
import com.training.aigoushopapi.util.RsaUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;

    /**
     * 登录业务逻辑
     *
     * @param userName 用户名
     * @param password 密码
     * @return 用户对象，登录成功返回用户对象，否则返回null
     */
    @Override
    public UserVO login(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        User user = getOne(queryWrapper);
        if (user != null && MD5Utils.md5(password).equals(user.getUserPwd())) {
            UserVO userVO = new UserVO();
            userVO.setUserId(user.getUserId());
            //生成Jwt字符串
            userVO.setToken(JwtUtils.generateToken(user.getUserName(), RsaUtils.privateKey,JwtUtils.EXPIRE_MINUTES));
            return userVO;
        } else {
            return null;
        }
    }
}
