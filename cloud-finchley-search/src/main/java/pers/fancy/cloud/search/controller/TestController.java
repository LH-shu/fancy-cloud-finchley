package pers.fancy.cloud.search.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.fancy.cloud.search.core.index.ElasticsearchIndex;
import pers.fancy.cloud.search.core.repository.*;
import pers.fancy.cloud.search.core.util.JsonUtils;
import pers.fancy.cloud.search.model.Main2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fancy
 * @time 2019/10/6 0006 22:20
 */
@Api("索引CURD与简单搜索")
@RestController
public class TestController {

    @Autowired
    ElasticsearchIndex elasticsearchIndex;

    @Autowired
    RestHighLevelClient client;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @ApiOperation("索引创建")
    @GetMapping("createIndex")
    public void createIndex() throws Exception {
        if(!elasticsearchIndex.exists(Main2.class)){
            elasticsearchIndex.dropIndex(Main2.class);
            elasticsearchIndex.createIndex(Main2.class);
        }
    }

    @ApiOperation("索引删除")
    @GetMapping("dropIndex")
    public void dropIndex() throws Exception {
        elasticsearchIndex.dropIndex(Main2.class);
    }

    @ApiOperation("保存单一对象")
    @GetMapping("saveSingle")
    public void saveSingle() throws Exception {
        Main2 main1 = new Main2();
        main1.setProposal_no("main1123123123");
        main1.setAppli_code("123");
        main1.setAppli_name("2");
        main1.setRisk_code("0501");
        main1.setSum_premium(100);
        elasticsearchTemplate.save(main1);
    }

    @ApiOperation("批量保存")
    @GetMapping("saveList")
    public void saveList() throws Exception {
        List<Main2> list = new ArrayList<>();
        Main2 main1 = new Main2();
        main1.setProposal_no("NO001");
        main1.setAppli_name("456");
        main1.setRisk_code("0101");
        main1.setSum_premium(1);
        Main2 main2 = new Main2();
        main2.setProposal_no("NO002");
        main2.setAppli_name("456");
        main2.setSum_premium(2);
        main2.setRisk_code("0102");
        Main2 main3 = new Main2();
        main3.setProposal_no("NO003");
        main3.setRisk_code("0103");
        main3.setSum_premium(3);
        main3.setAppli_name("456");
        list.add(main1);
        list.add(main2);
        list.add(main3);
        elasticsearchTemplate.save(list);
    }

    @ApiOperation("更新一个对象")
    @GetMapping("update")
    public void update() throws Exception {
        Main2 main1 = new Main2();
        main1.setProposal_no("NO001");
        main1.setInsured_code("updateNO001");
        elasticsearchTemplate.update(main1);
    }

    @ApiOperation("覆盖一个对象")
    @GetMapping("cover")
    public void testCoverUpdate()throws Exception {
        Main2 main1 = new Main2();
        main1.setProposal_no("main1");
        main1.setInsured_code("123");
        elasticsearchTemplate.updateCover(main1);
    }

    @ApiOperation("删除一个对象")
    @GetMapping("delete")
    public void testDelete()throws Exception {
        Main2 main1 = new Main2();
        main1.setProposal_no("main1");
        main1.setInsured_code("123");
        elasticsearchTemplate.delete(main1);
        elasticsearchTemplate.deleteById("main1",Main2.class);
    }

    public void testOri()throws Exception {
        SearchRequest searchRequest = new SearchRequest(new String[]{"index"});
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(new MatchAllQueryBuilder());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = elasticsearchTemplate.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Main2 t = JsonUtils.string2Obj(hit.getSourceAsString(), Main2.class);
            System.out.println(t);
        }
    }



    @GetMapping("testSearch")
    public void testSearch()throws Exception {
        List<Main2> main2List = elasticsearchTemplate.search(new MatchAllQueryBuilder(),Main2.class);
        main2List.forEach(main2 -> System.out.println(main2));
    }



    @GetMapping("testPage")
    public void testPage() throws IOException {
        SearchRequest searchRequest = new SearchRequest("index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(new MatchAllQueryBuilder());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);
        SearchResponse searchResponse = client.search(searchRequest.source(searchSourceBuilder), RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Main2 t = JsonUtils.string2Obj(hit.getSourceAsString(), Main2.class);
            System.out.println(t.toString());
        }
    }

    public void testsaveHighlight() throws Exception {
        Main2 main1 = new Main2();
        main1.setProposal_no("main1123123123");
        main1.setAppli_code("123");
        main1.setAppli_name("一二三四五唉收到弄得你");
        main1.setRisk_code("0501");
        main1.setSum_premium(100);
        elasticsearchTemplate.save(main1);
    }

    @GetMapping("testSearch2")
    public void testSearch2()throws Exception {
        int currentPage = 1;
        int pageSize = 10;
        //分页
        PageSortHighLight psh = new PageSortHighLight(currentPage,pageSize);
        //排序
        String sorter = "proposal_no.keyword";
        Sort.Order order = new Sort.Order(SortOrder.ASC,sorter);
        psh.setSort(new Sort(order));
        //定制高亮，如果定制了高亮，返回结果会自动替换字段值为高亮内容
        psh.setHighLight(new HighLight().field("appli_name"));
        //可以单独定义高亮的格式
        //new HighLight().setPreTag("<em>");
        //new HighLight().setPostTag("</em>");
        PageList<Main2> pageList;
        pageList = elasticsearchTemplate.search(QueryBuilders.matchQuery("appli_name","我"), psh, Main2.class);
        pageList = elasticsearchTemplate.search(new MatchAllQueryBuilder(), psh, Main2.class);
        pageList.getList().forEach(main2 -> System.out.println(main2));


        HighLight highLight = new HighLight();
        highLight.setPreTag("<span style=\"color:red\">");
        highLight.setPostTag("</span>");
        highLight.field("appli_code").field("appli_name");

//        psh.setHighLight(highLight);
//        PageList<Main2> pageList = new PageList<>();
//        pageList = elasticsearchTemplate.search(
//                QueryBuilders.matchQuery("appli_name","中男儿"),
//                psh, Main2.class);
    }

    public void testCount()throws Exception {
        long count = elasticsearchTemplate.count(new MatchAllQueryBuilder(),Main2.class);
        System.out.println(count);
    }


    public void testScroll()throws Exception {
        //默认scroll镜像保留2小时
        List<Main2> main2List = elasticsearchTemplate.scroll(new MatchAllQueryBuilder(),Main2.class);
        main2List.forEach(main2 -> System.out.println(main2));

        //指定scroll镜像保留5小时
        //List<Main2> main2List = elasticsearchTemplate.scroll(new MatchAllQueryBuilder(),Main2.class,5);
    }


    public void testCompletionSuggest()throws Exception {
        List<String> list = elasticsearchTemplate.completionSuggest("appli_name", "1", Main2.class);
        list.forEach(main2 -> System.out.println(main2));
    }


    public void testSearchByID()throws Exception {
        Main2 main2 = (Main2) elasticsearchTemplate.getById("main2", Main2.class);
        System.out.println(main2);
    }

    public void testMGET()throws Exception {
        String[] list = {"main2","main3"};
        List<Main2> listResult = elasticsearchTemplate.mgetById(list, Main2.class);
        listResult.forEach(main -> System.out.println(main));
    }

    @GetMapping("testQueryBuilder")
    public void testQueryBuilder() throws Exception {

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("appli_name.keyword","456"))
                .filter(QueryBuilders.matchPhraseQuery("risk_code","0101"));
        List<Main2> list = elasticsearchTemplate.search(queryBuilder,Main2.class);
        list.forEach(main2 -> System.out.println(main2));
    }
}
