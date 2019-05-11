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
//@Table(name = "SEARCH_LOG_WORD", uniqueConstraints = {}, catalog = "", schema = "SEARCH")
public class SearchRecordEntity {
    @ApiParam(name = "fdId", value = "搜索记录的ID值")
//	@Column(name = "FD_ID", columnDefinition = "VARCHAR2", table = "主键Id", length = 50, nullable = false)
    private String fdId;

    @ApiParam(name = "fdWord", value = "搜索的关键词")
//	@Column(name = "FD_SEARCH_WORD", columnDefinition = "VARCHAR2", table = "搜索的关键词", length = 50, nullable = true)
    private String fdWord;

    @ApiParam(name = "fdUserId", value = "发起搜索的用户ID")
//	@Column(name = "FD_USER_ID", columnDefinition = "VARCHAR2", table = "发起搜索的用户ID", length = 50, nullable = true)
    private String fdUserId;

    @ApiParam(name = "fdUserName", value = "发起搜索的用户姓名")
//	@Column(name = "FD_USER_NAME", columnDefinition = "VARCHAR2", table = "发起搜索的用户姓名", length = 50, nullable = true)
    private String fdUserName;

    @ApiParam(name = "fdSearchTime", value = "发起搜索的时间")
//	@Column(name = "FD_SEARCH_TIME", columnDefinition = "DATETIME", table = "发起搜索的时间", length = 26, nullable = true)
    private Date fdSearchTime;
}
