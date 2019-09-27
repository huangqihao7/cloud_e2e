package com.qihao.security;

import com.qihao.mapper.SysPrivilegeMapper;
import com.qihao.pojo.SysPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/26 17:53
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute resourceName;
        ConfigAttribute urlString;
        List<SysPrivilege> sysPrivilege = sysPrivilegeMapper.selectList(null);
        for (SysPrivilege privilege : sysPrivilege) {
            array = new ArrayList<>();
            resourceName = new SecurityConfig(privilege.getName());
            urlString = new SecurityConfig(privilege.getUrl());
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(urlString);
            array.add(resourceName);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(privilege.getUrl(), array);
        }
        /*array = new ArrayList<>();
        array.add(new SecurityConfig("/user"));
        array.add(new SecurityConfig("/user"));
        map.put("/user",array);*/
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (String s : map.keySet()) {
            resUrl = s;
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
