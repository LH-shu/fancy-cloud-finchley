package pers.fancy.cloud.search.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * 统一搜索查询条件dto
 */

@Data
@ApiModel(description = "统一搜索查询条件", value = "SearchConditionDto")
public class SearchConditionDto {


    @ApiParam(name = "pageNo", value ="当前页数，默认1")
    private Integer pageNo=1;

    @ApiParam(name = "pageCount", value ="每页条数，默认10")
    private Integer pageCount=10;

    @ApiParam(name = "keyword", value ="输入的关键字", required = true)
    private String keyword;

    @ApiParam(name = "security", value ="密级(1:公开,2:内部,3:秘密,4:机密) eg:[\"1\",\"3\"]")
    private List<String> security;

    @ApiParam(name = "app", value ="搜索范围(文档管控,个人网盘)   eg:[\"文档管控\",\"个人网盘\"]")
    private List<String> app;

    @ApiParam(name = "field", value ="搜索域(title:标题,ownerName:作者,content:正文,docType:附件) eg:[\"title\",\"ownerName\"]")
    private List<String> field;

    @ApiParam(name = "timeType", value ="时间段ONEDAY,ONEWEEK,ONEMONTH,ONEYEAR")
    private String timeType;

    @ApiParam(name = "userId", value ="搜索者id")
    private String userId;

    @ApiParam(name = "userRole", value ="搜索者角色")
    private String userRole;

    @ApiParam(name = "sortField", value ="排序字段createTime")
    private String sortField;

    @ApiParam(name = "sortOrder", value ="排序desc，asc")
    private String sortOrder;

}

