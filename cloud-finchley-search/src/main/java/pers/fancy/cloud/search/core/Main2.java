package pers.fancy.cloud.search.core;

import lombok.Data;
import pers.fancy.cloud.search.core.annotation.ESID;
import pers.fancy.cloud.search.core.annotation.ESMapping;
import pers.fancy.cloud.search.core.annotation.ESMetaData;
import pers.fancy.cloud.search.core.enums.DataType;

/**
 * @author fancy
 * @time 2019/10/6 0006 22:12
 */
@ESMetaData(indexName = "test")
@Data
public class Main2 {
    @ESID
    private String proposal_no;
    @ESMapping(datatype = DataType.keyword_type)
    private String business_nature_name;
    @ESMapping(datatype = DataType.text_type)
    private String appli_name;
    private String appli_code;
    private String risk_code;
    private Integer sum_premium;
    private String insured_code;
}
