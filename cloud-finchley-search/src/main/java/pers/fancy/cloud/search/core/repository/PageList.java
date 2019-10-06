package pers.fancy.cloud.search.core.repository;

import java.util.List;

/**
 * description: 分页对象封装
 **/
public class PageList<T> {
    List<T> list;

    private int totalPages = 0;

    private long totalElements = 0;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
