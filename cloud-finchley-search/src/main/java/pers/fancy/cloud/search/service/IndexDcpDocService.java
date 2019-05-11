package pers.fancy.cloud.search.service;

import org.springframework.web.bind.annotation.RequestBody;
import pers.fancy.cloud.search.model.es.DcpDoc;

import java.util.List;

/**
 * 索引增删改-接口
 */
public interface IndexDcpDocService {
    /**
     * 创建索引
     * @param dcpDoc 文档实例数据
     * @throws Exception 异常
     */
    public void createDcpDoc(@RequestBody DcpDoc dcpDoc) throws Exception;

    /**
     * 批量创建索引
     * @param dcpDocList 文档实例数据
     * @throws Exception 异常
     */
    public void createDcpDocBatch(@RequestBody List<DcpDoc> dcpDocList) throws Exception;

    /**
     * 删除索引
     * @param ids 文档ids
     * @throws Exception 异常
     */
    public void deleteDcpDoc(List<String> ids) throws Exception;

    /**
     * 删除索引
     * @param id 文档id
     * @throws Exception 异常
     */
    public void deleteDcpDocById(String id) throws Exception;

    /**
     * 根据id修改其它不为null的字段
     * @param dcpDoc 文档实例数据
     * @throws Exception 异常
     */
    public void updateDcpDoc(@RequestBody DcpDoc dcpDoc) throws Exception;


}
