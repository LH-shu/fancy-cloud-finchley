package pers.fancy.cloud.search.dao.repository;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import pers.fancy.cloud.search.config.ElasticSearchInit;
import pers.fancy.cloud.search.model.es.DcpDoc;

import javax.annotation.Resource;
import java.util.List;

/**
 * repository实现类
 */
@Slf4j
@Component
public class IndexDcpDocRepository {
    @Resource
    private RestHighLevelClient esClient;

    @Resource
    private RestClient restClient;

    /**
     * RestHighLevelClient创建索引
     * @param dcpDoc
     * @throws Exception
     */
    public void createDcpDoc(@RequestBody DcpDoc dcpDoc) throws Exception{
        JSONObject json = (JSONObject) JSONObject.toJSON(dcpDoc);
        IndexRequest indexRequest = new IndexRequest(ElasticSearchInit.INDEX_DCP,
                ElasticSearchInit.INDEX_DCP_DOC,dcpDoc.getId()).source(json);
        esClient.index(indexRequest);
    }

    /**
     * RestHighLevelClient批量创建索引
     * @param dcpDocList
     * @throws Exception
     */
    public void createDcpDocBatch(@RequestBody List<DcpDoc> dcpDocList) throws Exception{
        BulkRequest bulkRequest = new BulkRequest();
        dcpDocList.forEach(dcpDoc -> {
            JSONObject json = (JSONObject) JSONObject.toJSON(dcpDoc);
            bulkRequest.add(new IndexRequest(ElasticSearchInit.INDEX_DCP,ElasticSearchInit.INDEX_DCP_DOC,dcpDoc.getId()).source(json));
        });
        esClient.bulk(bulkRequest);
    }

    /**
     * RestHighLevelClient根据ids批量删除索引
     * @param ids
     * @throws Exception
     */
    public void deleteDcpDoc(List<String> ids) throws Exception{
        BulkRequest bulkRequest = new BulkRequest();
        ids.forEach(id->{
            bulkRequest.add(new DeleteRequest(ElasticSearchInit.INDEX_DCP, ElasticSearchInit.INDEX_DCP_DOC, id));
        });
        esClient.bulk(bulkRequest);
    }

    /**
     * RestHighLevelClient根据id删除索引
     * @param id
     * @throws Exception
     */
    public void deleteDcpDocById(String id) throws Exception{
        DeleteRequest deleteRequest = new DeleteRequest(ElasticSearchInit.INDEX_DCP, ElasticSearchInit.INDEX_DCP_DOC, id);
        esClient.delete(deleteRequest);
    }

    /**
     * RestHighLevelClient根据id更新属性
     * @param dcpDoc
     * @throws Exception
     */
    public void updateDcpDoc(@RequestBody DcpDoc dcpDoc) throws Exception{
        String json = JSONObject.toJSONString(dcpDoc);
        JSONObject jsonObject = JSONObject.parseObject(json);
        UpdateRequest updateRequest = new UpdateRequest(ElasticSearchInit.INDEX_DCP, ElasticSearchInit.INDEX_DCP_DOC, dcpDoc.getId()).doc(jsonObject);
        esClient.update(updateRequest);
    }

}
