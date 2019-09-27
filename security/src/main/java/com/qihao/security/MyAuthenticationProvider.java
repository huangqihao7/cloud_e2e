package com.qihao.security;

import com.qihao.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 实现自己的AuthenticationProvider类，用来自定义用户校验机制
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/6 11:21
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 获取用户
        UserDetails user = (UserDetail)sysUserService.selectUserByUserName(userName);
        // 验证用户，其它验证同样道理
        if(user == null || !user.getPassword().equals(password)) {
            throw new BadCredentialsException("账号密码错误");
        }

        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
