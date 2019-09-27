package com.qihao.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.qihao.common.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class SysRole extends BasePojo<SysRole> {

    private Integer id;

    private String roleName;

    private String code;

    private String description;

    @TableField(exist = false)
    private List<SysPrivilege> privileges;

}
