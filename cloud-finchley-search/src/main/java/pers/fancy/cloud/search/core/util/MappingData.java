package pers.fancy.cloud.search.core.util;

import lombok.Data;

/**
 * mapping注解对应的数据载体类
 *
 * @author LiLiChai
 */
@Data
public class MappingData {

    String field_name;
    /**
     * 数据类型（包含 关键字类型）
     *
     * @return
     */
    String datatype;
    /**
     * 间接关键字
     *
     * @return
     */
    boolean keyword;

    /**
     * 关键字忽略字数
     *
     * @return
     */
    int ignore_above;
    /**
     * 是否支持autocomplete，高效全文搜索提示
     *
     * @return
     */
    boolean autocomplete;
    /**
     * 是否支持suggest，高效前缀搜索提示
     *
     * @return
     */
    boolean suggest;
    /**
     * 索引分词器设置
     *
     * @return
     */
    String analyzer;
    /**
     * 搜索内容分词器设置
     *
     * @return
     */
    String search_analyzer;
//    /**
//     * 是否分析字段
//     * @return
//     */
//    String analyzedtype;

    private boolean allow_search;

    private String copy_to;

}
