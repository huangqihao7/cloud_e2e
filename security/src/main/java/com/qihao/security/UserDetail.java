package com.qihao.security;

import com.qihao.pojo.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/26 15:31
 */
@Data
public class UserDetail extends SysUser implements UserDetails {

    private  Collection<? extends GrantedAuthority> grantedAuthority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
