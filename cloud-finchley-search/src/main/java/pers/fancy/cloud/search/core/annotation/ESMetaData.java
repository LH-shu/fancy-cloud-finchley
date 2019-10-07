package pers.fancy.cloud.search.core.annotation;

import java.lang.annotation.*;

/**
 * es索引元数据的注解，在es entity class上添加
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ESMetaData {

    /**
     * 检索时的索引名称，如果不配置则默认为和indexName一致，该注解项仅支持搜索
     * 并不建议这么做，建议通过特定方法来做跨索引查询
     */
    String[] searchIndexNames() default {};

    /**
     * 索引名称，必须配置
     */
    String indexName();

    /**
     * 索引类型，可以不配置，不配置默认和indexName相同，墙裂建议每个index下只有一个type
     */
    String indexType() default "";

    /**
     * 主分片数量
     *
     * @return
     */
    int number_of_shards() default 5;

    /**
     * 备份分片数量
     *
     * @return
     */
    int number_of_replicas() default 1;

    /**
     * 是否打印日志
     *
     * @return
     */
    boolean printLog() default false;
}
