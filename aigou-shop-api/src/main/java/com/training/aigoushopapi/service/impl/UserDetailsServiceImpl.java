package com.training.aigoushopapi.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.training.aigoushopapi.entity.CustomUserDetails;
import com.training.aigoushopapi.entity.User;
import com.training.aigoushopapi.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 十五
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //按用户名查询用户
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, username));
        if (user == null) {
            throw new UsernameNotFoundException("username is not exists");
        }
        //包装用户信息 1)用户名 2）密码 3）权限列表
        // 返回自定义的 UserDetails
        return new CustomUserDetails(
                // 传入 userId
                user.getUserId(),
                user.getUserName(),
                user.getUserPwd(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("")
        );
    }
}
