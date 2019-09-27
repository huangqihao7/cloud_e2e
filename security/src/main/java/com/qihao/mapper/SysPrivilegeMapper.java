package com.qihao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qihao.pojo.SysPrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/26 16:09
 */
@Mapper
public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {

    List<SysPrivilege> selectPrivilegeByUsername(String username);
}
