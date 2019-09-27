package com.qihao.security;

import com.qihao.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/6 11:03
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthFailureHandler loginAuthFailureHandler;

    @Autowired
    private LoginAuthSuccessHandler loginAuthSuccessHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private RestAuthAccessDeniedHandler restAuthAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //"/login"不进行权限验证
                .antMatchers("/login").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated()   //其他的需要登陆后才能访问
                .and()
                .formLogin()
                //loginProcessingUrl用于指定前后端分离的时候调用后台登录接口的名称
                .loginProcessingUrl("/login")
                //配置登录成功的自定义处理类
                .successHandler(loginAuthSuccessHandler)
                //配置登录失败的自定义处理类
                .failureHandler(loginAuthFailureHandler)
                .and()
                .exceptionHandling()
                // 未登录情况下的处理
                .authenticationEntryPoint(macLoginUrlAuthenticationEntryPoint())
                .and()
                //loginProcessingUrl用于指定前后端分离的时候调用后台注销接口的名称
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .and()
                //配置没有权限的自定义处理类
                .exceptionHandling().accessDeniedHandler(restAuthAccessDeniedHandler)
                .and()
                .cors()//新加入
                .and()
                .csrf().disable();// 取消跨站请求伪造防护
    }

    @Bean
    public AuthenticationEntryPoint macLoginUrlAuthenticationEntryPoint() {
        return new WithoutLoginHandler("/login");
    }

}
