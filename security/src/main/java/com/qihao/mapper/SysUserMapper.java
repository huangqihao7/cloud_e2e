package com.qihao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qihao.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/6 14:45
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectByUsername(@Param("username") String username);
}
