package com.sayo.homeworkmanagementsystem.bean;

import java.util.List;

public class Page<T> {

    public static final int PAGE_SIZE = 4;
    private int pageNo;  // 当前页码
    private int pageSize = PAGE_SIZE;
    private int pageTotalCount; // 总记录数
    private int isMore;
    private int pageTotal; // 总页面数
    private List<T> items;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = Math.min(pageNo, pageTotal);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(int pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public int getIsMore() {
        return isMore;
    }

    public void setIsMore(int isMore) {
        this.isMore = isMore;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}