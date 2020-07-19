package com.ygz.aspen.common.base;

/**
 * 分页查询参数基础类
 */
public class PageQueryParam {

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

    public PageQueryParam() {
    }

    public PageQueryParam(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public PageQueryParam(Integer pageIndex, Integer pageSize, String orderByClause) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.orderByClause = orderByClause;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * 获取从第几条开始
     * @return
     */
    public int getStart() {
        if(this.pageIndex <= 1)
            return 0;
        else
            return (this.pageIndex - 1) * this.pageSize;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

}
