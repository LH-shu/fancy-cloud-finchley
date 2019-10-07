package pers.fancy.cloud.search.core.repository;

import lombok.Data;

import java.util.List;

/**
 * 分页对象封装
 *
 * @author 李醴茝
 */
@Data
public class PageList<T> {

    List<T> list;
    private int totalPages = 0;
    private long totalElements = 0;

}
