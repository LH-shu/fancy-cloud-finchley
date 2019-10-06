package pers.fancy.cloud.search.web.rest;


import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.fancy.cloud.search.common.page.ResultData;
import pers.fancy.cloud.search.model.dto.SearchConditionDto;
import pers.fancy.cloud.search.model.dto.SearchResultDto;
import pers.fancy.cloud.search.service.SearchDcpDocService;

import java.util.List;

/**
 *
 * 前端查询接口
 * @author 李醴茝
 */
@RestController
@Slf4j
@Api(tags = "前端查询接口", value = "前端查询接口")
@RequestMapping("/searchdcpdo")
public class SearchDcpDocController {

    @Autowired
    private SearchDcpDocService searchDcpDocService;

    @ApiOperation("统一搜索查询")
    @PostMapping("/search")
    public ResponseEntity<ResultData> search(@RequestBody SearchConditionDto searchConditionDto) {
        try {
            if(StringUtils.isEmpty(searchConditionDto.getKeyword())){
                throw  new Exception("关键词不能为空");
            }
            SearchResultDto searchResultDto =  searchDcpDocService.searchDcpDoc(searchConditionDto);
            return ResponseEntity.ok(ResultData.DATA(JSONObject.toJSON(searchResultDto)));
        } catch (Exception e) {
            log.error("搜索失败:"+e.getMessage(),e);
            return ResponseEntity.ok(ResultData.ERROR("搜索失败:"+e.getMessage()));
        }
    }

}

