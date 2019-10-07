package pers.fancy.cloud.search.model;

import lombok.Data;
import pers.fancy.cloud.search.core.annotation.ESID;
import pers.fancy.cloud.search.core.annotation.ESMetaData;

import java.io.Serializable;

/**
 * @author fancy
 * @time 2019/10/7 0007 9:26
 */
@ESMetaData(indexName = "testindex", indexType = "test")
@Data
public class Main3 implements Serializable {
    @ESID
    private String id;
    private String content;
}
