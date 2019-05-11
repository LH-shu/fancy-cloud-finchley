package pers.fancy.cloud.search.dao.repository;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.fancy.cloud.search.model.dto.SearchRecordDto;
import pers.fancy.cloud.search.model.entity.SearchRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 对查询记录的增加，删除和查询
 */

@Mapper
public interface DCPSearchRecordRepository {

    /**
     * 创建搜索记录
     * @param searchRecordEntity
     */
    void addRecord(SearchRecordEntity searchRecordEntity);

    /**
     * 输出搜索次数最多的前五条记录
    */
    List<SearchRecordDto> findHotRecord( @Param("map") Map<String, String> map);

    /**
     *根据记录id值来删除用户搜索记录
     * @param id
     */
    void deleteRecordByID(String id);

}
