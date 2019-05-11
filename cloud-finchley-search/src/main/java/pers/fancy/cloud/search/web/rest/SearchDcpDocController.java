package pers.fancy.cloud.search.web.rest;


import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.fancy.cloud.search.common.page.ResultData;
import pers.fancy.cloud.search.model.dto.SearchConditionDto;
import pers.fancy.cloud.search.model.dto.SearchResultDto;
import pers.fancy.cloud.search.service.DCPSearchRecordService;
import pers.fancy.cloud.search.service.SearchDcpDocService;

import java.util.List;

/**
 *
 * 前端查询接口
 */
@RestController
@Slf4j
@Api(tags = "前端查询接口", value = "前端查询接口")
@RequestMapping("/searchdcpdo")
public class SearchDcpDocController {


    @Autowired
    private SearchDcpDocService searchDcpDocService;

    @Autowired
    private DCPSearchRecordService dcpSearchRecordService;

    @Value("${hotWord.time}")
    private  Integer time ;

    @ApiOperation("统一搜索查询")
    @PostMapping("/search")
    public ResponseEntity<ResultData> search(@RequestBody SearchConditionDto searchConditionDto) {
        try {
            if(StringUtils.isEmpty(searchConditionDto.getKeyword())){
                throw  new Exception("关键词不能为空");
            }
            // TODO: 18/8/24 测试代码
            if(StringUtils.isEmpty(searchConditionDto.getUserId())){
//                searchConditionDto.setUserId(RedisUtil.getUserId());
            }
            //把搜索关键词存入搜索记录表
            dcpSearchRecordService.addSearchRecord(searchConditionDto.getKeyword());
            SearchResultDto searchResultDto =  searchDcpDocService.searchDcpDoc(searchConditionDto);

            return ResponseEntity.ok(ResultData.DATA(JSONObject.toJSON(searchResultDto)));
        } catch (Exception e) {
            log.error("搜索失败:"+e.getMessage(),e);
            return ResponseEntity.ok(ResultData.ERROR("搜索失败:"+e.getMessage()));
        }
    }

    @GetMapping("/listHotWord")
    @ApiOperation(value = "输出热词")
    public ResponseEntity<ResultData> listHotWord() {
        List<String> hotWordList  = dcpSearchRecordService.findHotWord(time);
        return ResponseEntity.ok(ResultData.DATA(hotWordList));
    }

}

