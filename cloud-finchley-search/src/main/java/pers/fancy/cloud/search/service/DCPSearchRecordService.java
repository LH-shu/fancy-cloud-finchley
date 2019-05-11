package pers.fancy.cloud.search.service;


import pers.fancy.cloud.search.model.entity.SearchRecordEntity;

import java.util.List;

/**
 * 对搜索记录的增删
 */
public interface DCPSearchRecordService {
    /**
     * 创建搜索记录
     * @param searchRecordWord
     * @return
     */
    public SearchRecordEntity addSearchRecord(String searchRecordWord) throws Exception;

    /**
     * 通过输入起始时间来展示这段时间的热词
     * @param
     * @return
     */
    public List<String> findHotWord(Integer time);

    /**
     * 根据ID删除搜索记录
     * @param id
     */
    public void deleteSearchRecord(String id) throws Exception;
}
