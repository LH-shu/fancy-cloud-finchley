package pers.fancy.cloud.search.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 公用分页对象
 */
public class PageBean<T> implements Serializable {


    private Integer currentPage = 1; // 当前页码
    private Integer pageSize = 10;   // 每页记录数
    private Integer totalCount = 0;  // 总记录数
    private Integer pageCount = 0;   // 总页数
    private List<T> resultList;      // 返回的查询结果集



    public Integer getPageCount() {
        pageCount = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            pageCount ++;
        }
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
