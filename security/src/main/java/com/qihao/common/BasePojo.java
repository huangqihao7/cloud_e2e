package com.qihao.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/6 14:32
 */
@Data
public class BasePojo<T> {

    private String created;
    private Date createdTime;
    private String updated;
    private Date updatedTime;

    @TableField(exist = false)
    private Long total;

    @TableField(exist = false)
    private List<T> rows;
}
