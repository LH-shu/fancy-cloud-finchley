package pers.fancy.cloud.search.service;


import pers.fancy.cloud.search.model.dto.SearchConditionDto;
import pers.fancy.cloud.search.model.dto.SearchResultDto;

/**
 * 前端查询接口
 */
public interface SearchDcpDocService {

    /**
     *
     * 分页查询es数据
     * @param searchConditionDto
     * @return
     * @throws Exception
     */
   public SearchResultDto searchDcpDoc(SearchConditionDto searchConditionDto) throws Exception;

}
