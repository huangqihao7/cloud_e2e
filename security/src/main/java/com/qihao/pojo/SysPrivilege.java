package com.qihao.pojo;

import com.qihao.common.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangqihao
 * @since 2019-09-26
 */
@Data
public class SysPrivilege extends BasePojo<SysPrivilege> {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer type;

    private Integer orderNum;

    private String code;

    private String url;

}
