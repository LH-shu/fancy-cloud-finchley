package pers.fancy.cloud.search.core.repository;

import lombok.Data;

/**
 * 分页+高亮对象封装
 *
 * @author 李醴茝
 */
@Data
public class PageSortHighLight {

    private int currentPage;

    private int pageSize;
    Sort sort = new Sort();
    private HighLight highLight = new HighLight();

    public PageSortHighLight(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PageSortHighLight(int currentPage, int pageSize, Sort sort) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.sort = sort;
    }

}
