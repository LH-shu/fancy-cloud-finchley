package pers.fancy.cloud.search.model;


import lombok.Data;
import pers.fancy.cloud.search.core.annotation.ESID;
import pers.fancy.cloud.search.core.annotation.ESMapping;
import pers.fancy.cloud.search.core.annotation.ESMetaData;
import pers.fancy.cloud.search.core.enums.DataType;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: ${description}
 **/
@Data
@ESMetaData(indexName = "index",indexType = "main4", number_of_shards = 5,number_of_replicas = 0,printLog = false)
public class Main2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @ESID
    private String proposal_no;
    @ESMapping(datatype = DataType.keyword_type)
    private String risk_code;
    @ESMapping(datatype = DataType.text_type)
    private String risk_name;
    @ESMapping(keyword = true)
    private String business_nature;
    @ESMapping(datatype = DataType.text_type)
    private String business_nature_name;
    private String appli_code;//可以用默认值，这样会有appli_code.keyword可以直接搜
    @ESMapping(suggest = true)
    private String appli_name;
    private String insured_code;
    @ESMapping(autocomplete = true)
    private String insured_name;
    @ESMapping(datatype = DataType.date_type)
    private Date operate_date;
    @ESMapping(datatype = DataType.text_type)
    private String operate_date_format;
    @ESMapping(datatype = DataType.date_type)
    private Date start_date;
    @ESMapping(datatype = DataType.date_type)
    private Date end_date;
    @ESMapping(datatype = DataType.double_type)
    private double sum_amount;
    @ESMapping(datatype = DataType.double_type)
    private double sum_premium;
    @ESMapping(datatype = DataType.keyword_type)
    private String com_code;

    @Override
    public String toString() {
        return "Main2{" +
                ", proposal_no='" + proposal_no + '\'' +
                ", risk_code='" + risk_code + '\'' +
                ", risk_name='" + risk_name + '\'' +
                ", business_nature='" + business_nature + '\'' +
                ", business_nature_name='" + business_nature_name + '\'' +
                ", appli_code='" + appli_code + '\'' +
                ", appli_name='" + appli_name + '\'' +
                ", insured_code='" + insured_code + '\'' +
                ", insured_name='" + insured_name + '\'' +
                ", operate_date=" + operate_date +
                ", operate_date_format='" + operate_date_format + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", sum_amount=" + sum_amount +
                ", sum_premium=" + sum_premium +
                ", com_code='" + com_code + '\'' +
                '}';
    }
}
