package com.qihao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qihao.mapper.SysPrivilegeMapper;
import com.qihao.mapper.SysUserMapper;
import com.qihao.pojo.SysPrivilege;
import com.qihao.pojo.SysRole;
import com.qihao.pojo.SysUser;
import com.qihao.security.UrlGrantedAuthority;
import com.qihao.security.UserDetail;
import com.qihao.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/6 14:47
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    @Override
    public SysUser selectUserByUserName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("username", username);
        SysUser sysUser = (UserDetail) sysUserMapper.selectOne(queryWrapper);

        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<SysRole> roles = sysUser.getRoles();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //将所有的角色对应的资源权限全部放入user对应的grantedAuthority集合中
        for (SysRole role : roles) {
            List<SysPrivilege> privileges = role.getPrivileges();
            for (SysPrivilege privilege : privileges) {
                if (privilege != null && privilege.getName() != null) {
                    GrantedAuthority grantedAuthority = new UrlGrantedAuthority(privilege.getUrl());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
        }
        return sysUser;
    }
}
