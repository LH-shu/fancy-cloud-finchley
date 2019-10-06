package pers.fancy.cloud.search.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * 用户搜索的词条记录
 */

@Data
@ApiModel(description = "存储搜索的记录" , value = "SearchRecordEntity")
public class SearchRecordEntity {
    @ApiParam(name = "fdId", value = "搜索记录的ID值")
    private String fdId;

    @ApiParam(name = "fdWord", value = "搜索的关键词")
    private String fdWord;

    @ApiParam(name = "fdUserId", value = "发起搜索的用户ID")
    private String fdUserId;

    @ApiParam(name = "fdUserName", value = "发起搜索的用户姓名")
    private String fdUserName;

    @ApiParam(name = "fdSearchTime", value = "发起搜索的时间")
    private Date fdSearchTime;
}
