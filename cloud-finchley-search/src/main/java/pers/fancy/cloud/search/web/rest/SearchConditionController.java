package pers.fancy.cloud.search.web.rest;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.fancy.cloud.search.common.page.ResultData;
import pers.fancy.cloud.search.model.enums.SearchAppEnum;
import pers.fancy.cloud.search.model.enums.SearchFieldEnum;
import pers.fancy.cloud.search.model.enums.SearchSecurityEnum;
import pers.fancy.cloud.search.model.enums.SearchTimeTypeEnum;
import pers.fancy.cloud.search.service.SearchDcpDocService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 前端查询接口
 */
@RestController
@Slf4j
@Api(tags = "前端查询条件接口", value = "前端查询条件接口")
@RequestMapping("/searchcondition")
public class SearchConditionController {


    @Autowired
    private SearchDcpDocService searchDcpDocService;

    @ApiOperation("前端查询条件接口")
    @GetMapping("/list")
    public ResponseEntity<ResultData> list() {
        try {
            Map map  = new HashMap(4);
            //密级
            List security = new ArrayList();
            for (SearchSecurityEnum searchSecurityEnum : SearchSecurityEnum.values()) {
                Map mapSecurity  = new HashMap(10);
                mapSecurity.put(searchSecurityEnum.getCode(),searchSecurityEnum.getDesc());
                security.add(mapSecurity);
            }
            map.put("security",security);

            //搜索范围
            List app = new ArrayList();
            for (SearchAppEnum searchAppEnum : SearchAppEnum.values()) {
                Map mapApp  = new HashMap(10);
                mapApp.put(searchAppEnum.getDesc(),searchAppEnum.getDesc());
                app.add(mapApp);
            }
            map.put("app",app);

            //搜索域
            List field = new ArrayList();
            for (SearchFieldEnum searchFieldEnum : SearchFieldEnum.values()) {
                Map mapField  = new HashMap(10);
                mapField.put(searchFieldEnum.name(),searchFieldEnum.getDesc());
                field.add(mapField);
            }
            map.put("field",field);

            //搜索时间
            List timeType = new ArrayList();
            for (SearchTimeTypeEnum searchTimeTypeEnum : SearchTimeTypeEnum.values()) {
                Map mapTimeType  = new HashMap(10);
                mapTimeType.put(searchTimeTypeEnum.name(),searchTimeTypeEnum.getDesc());
                timeType.add(mapTimeType);
            }
            map.put("timeType",timeType);

            return ResponseEntity.ok(ResultData.DATA(map));
        } catch (Exception e) {
            log.error("查询条件失败:"+e.getMessage(),e);
            return ResponseEntity.ok(ResultData.ERROR("查询条件失败:"+e.getMessage()));
        }
    }

}

