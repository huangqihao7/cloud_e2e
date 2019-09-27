package com.qihao.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.qihao.common.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangqihao
 * @since 2019-09-26
 */
@Data
public class SysUser extends BasePojo<SysUser> {

    private Integer id;

    private String username;

    private String password;

    private String name;

    private Integer isDisabled;

    @TableField(exist = false)
    private List<SysRole> roles;

    @TableField(exist = false)
    private List<SysPrivilege> privileges;

}
