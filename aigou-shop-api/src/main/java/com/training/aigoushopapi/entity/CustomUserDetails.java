package com.training.aigoushopapi.entity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author 十五
 */
// 自定义用户详情
public class CustomUserDetails implements UserDetails {
    @Getter
    private final String userId;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> grantedAuthorities;

    public CustomUserDetails(String userId, String username, String password, List<GrantedAuthority> grantedAuthorities1) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
