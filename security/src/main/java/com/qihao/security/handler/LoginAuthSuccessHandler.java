package com.qihao.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qihao.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理类
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/6 10:16
 */
@Component
public class LoginAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SysUser user = (SysUser) authentication.getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","登录成功");
        map.put("user",user);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
