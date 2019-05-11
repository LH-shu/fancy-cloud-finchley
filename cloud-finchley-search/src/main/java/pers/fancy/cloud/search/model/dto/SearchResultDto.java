package pers.fancy.cloud.search.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import pers.fancy.cloud.search.common.page.PageBean;
import pers.fancy.cloud.search.model.es.DcpDoc;

import java.util.Map;

/**
 * 统一搜索查询返回数据
 */

@Data
@ApiModel(description = "统一搜索查询返回数据", value = "SearchResultDto")
public class SearchResultDto {
    /**
     * 分页数据
     */
    private PageBean<DcpDoc> page;
    /**
     * 聚合app包含查询数量
     */
    private Map<String, Long> appNum;
}

