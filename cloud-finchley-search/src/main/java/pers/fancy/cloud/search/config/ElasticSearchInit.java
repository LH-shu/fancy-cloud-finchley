package pers.fancy.cloud.search.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;


@Slf4j
@Component
public class ElasticSearchInit implements ApplicationRunner {

    /**Dcp索引*/
    public static final String INDEX_DCP  = "dcp3";
    /**DcpDoc对象type*/
    public static final String INDEX_DCP_DOC  = "dcp_doc3";

    @Value(value="classpath:config/dcpdoc_mappings.json")
    private Resource resource;

    @Value(value="classpath:config/dcpdoc_setting.json")
    private Resource resourceSetting;


    @javax.annotation.Resource
    private RestHighLevelClient esClient;
    @javax.annotation.Resource
    private RestClient restClient;
    /**
     * 初始化es创建索引mapping
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        GetIndexRequest getIndexRequestrequest = new GetIndexRequest();
        getIndexRequestrequest.indices(ElasticSearchInit.INDEX_DCP);
        boolean exists = esClient.indices().exists(getIndexRequestrequest);
        if(!exists) {
            log.info("<<<创建 elasticSearch index>>>");
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(ElasticSearchInit.INDEX_DCP);
            esClient.indices().create(createIndexRequest);

            JSONObject settingJson = JSONObject.parseObject(resourceSetting.getInputStream(),JSONObject.class);
            log.info(settingJson.toJSONString());
            HttpEntity entity = new NStringEntity(settingJson.toJSONString(), ContentType.APPLICATION_JSON);
            Map<String, String> params = Collections.emptyMap();
            Response response = restClient.performRequest("PUT", "/"+ElasticSearchInit.INDEX_DCP, params, entity);
            log.info(String.valueOf(response.getStatusLine().getStatusCode()));

            log.info("<<<创建 elasticSearch mapping>>>");
            JSONObject mappingJson = JSONObject.parseObject(resource.getInputStream(),JSONObject.class);
            log.info(mappingJson.toJSONString());
            PutMappingRequest putMappingRequest = new PutMappingRequest(ElasticSearchInit.INDEX_DCP);
            putMappingRequest.type(ElasticSearchInit.INDEX_DCP_DOC);
            putMappingRequest.source(mappingJson);
            esClient.indices().putMapping(putMappingRequest);
        }else{
            log.info("<<<初始化索引：已存在>>>");
        }

    }


}