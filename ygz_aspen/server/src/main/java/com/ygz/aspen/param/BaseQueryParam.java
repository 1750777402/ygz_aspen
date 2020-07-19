package com.ygz.aspen.param;

import com.ygz.aspen.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基础类
 */
@Data
public class BaseQueryParam extends BaseModel implements Serializable {


    /**
     * 第几页
     */
    private Integer pageIndex;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 排序 例：created desc
     */
    private String orderByClause;

    public BaseQueryParam() {
    }

    public BaseQueryParam(Integer pageIndex, Integer pageSize, String orderByClause) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.orderByClause = orderByClause;
    }
}