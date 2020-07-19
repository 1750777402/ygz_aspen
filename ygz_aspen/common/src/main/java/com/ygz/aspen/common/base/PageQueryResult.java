package com.ygz.aspen.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageQueryResult<T> implements Serializable {

    private Integer pageIndex;

    private Integer pageSize;

    private List<T> dataList;

    private Integer total;

    public PageQueryResult() {
    }

    public PageQueryResult(List<T> dataList, Integer total, Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.dataList = dataList;
        this.total = total;
    }

    public boolean isNotEmpty(){
        return this.dataList != null && this.dataList.size() > 0;
    }

    public boolean isEmpty(){
        return this.dataList == null || this.dataList.size() < 1;
    }
}
