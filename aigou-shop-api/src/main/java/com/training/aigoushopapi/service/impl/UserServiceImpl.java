package com.training.aigoushopapi.service.impl;

import com.training.aigoushopapi.entity.User;
import com.training.aigoushopapi.mapper.UserMapper;
import com.training.aigoushopapi.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
