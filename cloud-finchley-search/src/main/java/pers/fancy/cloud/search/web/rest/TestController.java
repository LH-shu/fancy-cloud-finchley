package pers.fancy.cloud.search.web.rest;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.fancy.cloud.search.common.page.ResultData;
import pers.fancy.cloud.search.common.page.ResultStatusEnum;
import pers.fancy.cloud.search.dao.repository.IndexDcpDocRepository;
import pers.fancy.cloud.search.model.News;
import pers.fancy.cloud.search.model.es.DcpDoc;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 测试类
 */
@RestController
@Slf4j
@Api(tags = "测试controller", value = "测试123")
public class TestController {


    @Autowired
    private MessageSource messageSource;

    @Resource
    private RestHighLevelClient esClient;

    @Resource
    private RestClient restClient;

//    @Resource
//    private DcpSysSearchApi dcpSysSearchApi;

    @Value(value = "classpath:config/dcpdoc_mappings.json")
    private org.springframework.core.io.Resource resource;


    @Resource()
    private IndexDcpDocRepository indexDcpDocRepository;

    String index = "demo";
    String type = "demo";

    @ApiOperation("添加一条索引")
    @GetMapping("addOne")
    public ResponseEntity<ResultData> demo2() {
        try {

            IndexRequest indexRequest = new IndexRequest(index, type);
            News news = new News();
            news.setTitle("中国产小型无人机的“对手”来了，俄微型拦截导弹便宜量又多");
            news.setTag("军事");
            news.setPublishTime("2018-01-24T23:59:30Z");
            String source = JSON.toJSONString(news);
            indexRequest.source(source, XContentType.JSON);
            IndexResponse result = esClient.index(indexRequest);
            return ResponseEntity.ok(ResultData.DATA("ok"));
        } catch (Exception e) {
            log.error("demo2失败:" + e.getMessage(), e);
            return ResponseEntity.ok(new ResultData(ResultStatusEnum.ERROR.getStatus(), "demo2失败:" + e.getMessage()));
        }
    }

    @GetMapping("addAll")
    public String addAll() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        List<IndexRequest> requests = generateRequests();
        for (IndexRequest indexRequest : requests) {
            bulkRequest.add(indexRequest);
        }
        Object result = esClient.bulk(bulkRequest);
        return "ok";
    }

    public List<IndexRequest> generateRequests() {
        List<IndexRequest> requests = new ArrayList<>();
        requests.add(generateNewsRequest("中印边防军于拉达克举行会晤 强调维护边境和平", "军事", "2018-01-27T08:34:00Z"));
        requests.add(generateNewsRequest("费德勒收郑泫退赛礼 进决赛战西里奇", "体育", "2018-01-26T14:34:00Z"));
        requests.add(generateNewsRequest("欧文否认拿动手术威胁骑士 兴奋全明星联手詹皇", "体育", "2018-01-26T08:34:00Z"));
        requests.add(generateNewsRequest("皇马官方通告拉莫斯伊斯科伤情 将缺阵西甲关键战", "体育", "2018-01-26T20:34:00Z"));
        return requests;
    }

    public IndexRequest generateNewsRequest(String title, String tag, String publishTime) {


        IndexRequest indexRequest = new IndexRequest(index, type);
        News news = new News();
        news.setTitle(title);
        news.setTag(tag);
        news.setPublishTime(publishTime);
        String source = JSON.toJSONString(news);
        indexRequest.source(source, XContentType.JSON);
        return indexRequest;
    }


    @ApiOperation("查询索引")
    @GetMapping(value = "/test/search")
    public ResponseEntity<ResultData> search() throws Exception {
        String index = "demo";
        String type = "demo";

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        sourceBuilder.fetchSource(new String[]{"title"}, new String[]{});
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "费德勒");
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("tag", "体育");
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime");
//        rangeQueryBuilder.gte("2018-01-22T08:00:00Z");
//        rangeQueryBuilder.lte("2019-01-26T20:00:00Z");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(matchQueryBuilder);
//        boolBuilder.must(termQueryBuilder);
//        boolBuilder.must(rangeQueryBuilder);
        sourceBuilder.query(boolBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        SearchResponse response = esClient.search(searchRequest);

        return ResponseEntity.ok(ResultData.DATA(response));

    }


    @ApiOperation("初始化index和mapping")
    @GetMapping(value = "/test/init")
    public ResponseEntity<ResultData> init() throws Exception {

//        GetIndexRequest getIndexRequestrequest = new GetIndexRequest();
//        getIndexRequestrequest.indices(ElasticSearchInit.INDEX_DCP);
//        boolean exists = esClient.indices().exists(getIndexRequestrequest);
//        if (!exists) {
//            log.info("<<<创建 elasticSearch index>>>");
//            CreateIndexRequest createIndexRequest = new CreateIndexRequest(ElasticSearchInit.INDEX_DCP);
//            esClient.indices().create(createIndexRequest);
//
//            log.info("<<<创建 elasticSearch mapping>>>");
//            JSONObject mappingJson = JSONObject.parseObject(resource.getInputStream(), JSONObject.class);
//            log.info(mappingJson.toJSONString());
//            PutMappingRequest putMappingRequest = new PutMappingRequest(ElasticSearchInit.INDEX_DCP);
//            putMappingRequest.type(ElasticSearchInit.INDEX_DCP_DOC);
//            putMappingRequest.source(mappingJson);
//            esClient.indices().putMapping(putMappingRequest);
//        }
        return ResponseEntity.ok(ResultData.DATA("ok"));
    }

    @ApiOperation("ES低级客户端")
    @GetMapping(value = "/test/lowlevel")
    public ResponseEntity<ResultData> lowlevel() throws Exception {
        Map<String, String> params = Collections.emptyMap();
        DcpDoc dcpDoc = new DcpDoc();
        dcpDoc.setTitle("mytitle标题");
        dcpDoc.setContent("mycontent内容");
        dcpDoc.setCreateTime(new Date());
        HttpEntity entity = new NStringEntity(JSONObject.toJSONString(dcpDoc), ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("POST", "/dcp/dcp_doc", params, entity);
        log.info(String.valueOf(response.getStatusLine().getStatusCode()));
        return ResponseEntity.ok(ResultData.DATA("ok"));
    }


    @ApiOperation("索引创建")
    @PostMapping(value = "/test/peterdao")
    public ResponseEntity<ResultData> peterdao(
            @RequestParam("method") String method,
            @RequestBody DcpDoc dcpDoc) throws Exception {

        if ("createDcpDoc".equals(method)) {
            indexDcpDocRepository.createDcpDoc(dcpDoc);
        } else if ("createDcpDocBatch".equals(method)) {
            List<DcpDoc> list = new ArrayList<>();
            list.add(dcpDoc);
            indexDcpDocRepository.createDcpDocBatch(list);
        } else if ("updateDcpDoc".equals(method)) {
            indexDcpDocRepository.updateDcpDoc(dcpDoc);
        } else {
            return ResponseEntity.ok(ResultData.DATA("不支持的方法"));
        }
        return ResponseEntity.ok(ResultData.DATA("ok"));
    }

    @ApiOperation("索引删除")
    @PostMapping(value = "/test/peterdaoDelete")
    public ResponseEntity<ResultData> peterdaoDelete(
            @RequestBody List<String> ids) throws Exception {

        indexDcpDocRepository.deleteDcpDoc(ids);
        return ResponseEntity.ok(ResultData.DATA("ok"));
    }

}

