package pers.fancy.cloud.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import pers.fancy.cloud.search.common.page.PageBean;
import pers.fancy.cloud.search.config.ElasticSearchInit;
import pers.fancy.cloud.search.model.dto.SearchConditionDto;
import pers.fancy.cloud.search.model.dto.SearchResultDto;
import pers.fancy.cloud.search.model.enums.SearchFieldEnum;
import pers.fancy.cloud.search.model.enums.SearchSecurityEnum;
import pers.fancy.cloud.search.model.enums.SearchTimeTypeEnum;
import pers.fancy.cloud.search.model.es.DcpDoc;
import pers.fancy.cloud.search.model.es.DcpDocStatusEnum;
import pers.fancy.cloud.search.service.SearchDcpDocService;
import pers.fancy.cloud.search.util.CommonDateUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 前端查询接口
 *
 */
@Slf4j
@Service
public class SearchDcpDocServiceImpl implements SearchDcpDocService {

    @Resource
    private RestHighLevelClient esClient;

    /**
     * 查询数据
     * @param searchConditionDto
     * @return
     * @throws Exception
     */
    @Override
    public SearchResultDto searchDcpDoc(SearchConditionDto searchConditionDto) throws Exception{
        SearchResultDto searchResultDto = new SearchResultDto();
        PageBean page = new PageBean();
        page.setCurrentPage(searchConditionDto.getPageNo());
        page.setPageSize(searchConditionDto.getPageCount());

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页信息
        searchSourceBuilder.from((page.getCurrentPage() - 1) * page.getPageSize() );
        searchSourceBuilder.size(page.getPageSize());

        //bool查询开始
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

        //文档状态过滤
        TermQueryBuilder termQueryBuilderDocStatus = QueryBuilders.termQuery("docStatus", DcpDocStatusEnum.VAILD.getCode());
        boolBuilder.must(termQueryBuilderDocStatus);

        //登录账号过滤
        if(StringUtils.isNotEmpty(searchConditionDto.getUserId())){
            TermsQueryBuilder termsQueryBuilderOwner = QueryBuilders.termsQuery("canSearchUser", searchConditionDto.getUserId(),"public");
            boolBuilder.must(termsQueryBuilderOwner);
        }

        ///按关键词查询
        if(searchConditionDto.getField()!=null && !searchConditionDto.getField().isEmpty()){
            List<String> fieldTemp = new ArrayList();
            searchConditionDto.getField().forEach(field->{
                if(SearchFieldEnum.docType.name().equals(field)){
                    TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("docType", "dcp");
                    boolBuilder.mustNot(termQueryBuilder);
                }else{
                    fieldTemp.add(field);
                }
            });
            if(fieldTemp.size()>0){
                String[] fieldArray = new String[fieldTemp.size()];
                fieldTemp.toArray(fieldArray);
                MultiMatchQueryBuilder  matchQueryBuilderMulti = QueryBuilders.multiMatchQuery(searchConditionDto.getKeyword(),fieldArray);
                boolBuilder.must(matchQueryBuilderMulti);
            }

        }else{
            MultiMatchQueryBuilder  matchQueryBuilderMulti = QueryBuilders.multiMatchQuery(searchConditionDto.getKeyword(),new String[]{"title","content","ownerName"});
            boolBuilder.must(matchQueryBuilderMulti);
        }


        //按时间查询
        if (StringUtils.isNotEmpty(searchConditionDto.getTimeType())) {
            Date nowDate = new Date();
            Date earlyInTheDay = CommonDateUtils.getEarlyInTheDay(nowDate);
            Date lateInTheDay = CommonDateUtils.getLateInTheDay(nowDate);

            Date dateStart = null;
            Date dateEnd = lateInTheDay;
            if (SearchTimeTypeEnum.ONEDAY.name().equals(searchConditionDto.getTimeType())) {
                dateStart = earlyInTheDay;
            }
            if (SearchTimeTypeEnum.ONEWEEK.name().equals(searchConditionDto.getTimeType())) {
                dateStart = CommonDateUtils.add(earlyInTheDay, CommonDateUtils.TimeUnit.WEEK_OF_MONTH, -1);
            }
            if (SearchTimeTypeEnum.ONEMONTH.name().equals(searchConditionDto.getTimeType())) {
                dateStart = CommonDateUtils.add(earlyInTheDay, CommonDateUtils.TimeUnit.MONTH, -1);
            }
            if (SearchTimeTypeEnum.ONEYEAR.name().equals(searchConditionDto.getTimeType())) {
                dateStart = CommonDateUtils.add(earlyInTheDay, CommonDateUtils.TimeUnit.YEAR, -1);
            }
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime");
            rangeQueryBuilder.gte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateStart));
            rangeQueryBuilder.lte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateEnd));
            boolBuilder.must(rangeQueryBuilder);
        }

        //密级查询
        if(searchConditionDto.getSecurity()!=null && !searchConditionDto.getSecurity().isEmpty()){
            TermsQueryBuilder termsQueryBuilderSecurity = QueryBuilders.termsQuery("security", searchConditionDto.getSecurity());
            boolBuilder.must(termsQueryBuilderSecurity);
        }

        //搜索范围查询
        if(searchConditionDto.getApp()!=null && !searchConditionDto.getApp().isEmpty()){
            TermsQueryBuilder termsQueryBuilderApp = QueryBuilders.termsQuery("app", searchConditionDto.getApp());
            boolBuilder.must(termsQueryBuilderApp);
        }
        searchSourceBuilder.query(boolBuilder);


        //排序
        if(StringUtils.isEmpty(searchConditionDto.getSortField())){
            searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        }else{
            if(SortOrder.DESC.toString().equals(searchConditionDto.getSortOrder())){
                searchSourceBuilder.sort(new FieldSortBuilder(searchConditionDto.getSortField()).order(SortOrder.DESC));
            }else{
                searchSourceBuilder.sort(new FieldSortBuilder(searchConditionDto.getSortField()).order(SortOrder.ASC));
            }
        }


        //高亮处理
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("title").field("content").field("ownerName");
        searchSourceBuilder.highlighter(highlightBuilder);

        //聚合
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("appdocnum").field("app");
        searchSourceBuilder.aggregation(aggregation);

        log.info(searchSourceBuilder.toString());
        SearchRequest searchRequest = new SearchRequest(ElasticSearchInit.INDEX_DCP);
        searchRequest.types(ElasticSearchInit.INDEX_DCP_DOC);
        searchRequest.source(searchSourceBuilder);

        //结果处理-hit数据
        SearchResponse response = esClient.search(searchRequest);
        List list = new ArrayList();
        response.getHits().forEach(hit->{
            DcpDoc dcpDoc = transferHitToDcpDoc(hit);
            list.add(dcpDoc);
        });
        page.setResultList(list);
        page.setTotalCount((int)response.getHits().getTotalHits());
        searchResultDto.setPage(page);

        //结果处理-聚合数据之按应用统计
        Map<String,Long> appNum = new HashMap<>(10);
        ParsedStringTerms genders = response.getAggregations().get("appdocnum");
        for (Terms.Bucket entry : genders.getBuckets()) {
            appNum.put(String.valueOf(entry.getKey()),entry.getDocCount());
        }
        searchResultDto.setAppNum(appNum);

        return searchResultDto;
    }


    /**
     * hit数据转换为dcpdoc
     * @param searchHit
     * @return
     */
    private DcpDoc transferHitToDcpDoc(SearchHit searchHit){

        DcpDoc dcpDoc =  JSONObject.parseObject(searchHit.getSourceAsString(),DcpDoc.class);
        HighlightField docContentHighlightField=searchHit.getHighlightFields().get("content");
        HighlightField docTitleHighlightField =searchHit.getHighlightFields().get("title");
        HighlightField docOwnerNameHighlightField =searchHit.getHighlightFields().get("ownerName");
        if(docContentHighlightField!=null){
            List<String> docContentHighLight = Arrays.stream(docContentHighlightField.fragments()).map(Text::string).collect(Collectors.toList());
            StringBuilder sb =new StringBuilder();
            docContentHighLight.stream().forEach(sb::append);
            dcpDoc.setContent(sb.toString());
        }
        if(docTitleHighlightField!=null){
            List<String> docTitleHighLight = Arrays.stream(docTitleHighlightField.fragments()).map(Text::string).collect(Collectors.toList());
            StringBuilder sb =new StringBuilder();
            docTitleHighLight.stream().forEach(sb::append);
            dcpDoc.setTitle(sb.toString());
        }
        if(docOwnerNameHighlightField!=null){
            List<String> docOwnerNameHighlight = Arrays.stream(docOwnerNameHighlightField.fragments()).map(Text::string).collect(Collectors.toList());
            StringBuilder sb =new StringBuilder();
            docOwnerNameHighlight.stream().forEach(sb::append);
            dcpDoc.setOwnerName(sb.toString());
        }
        if(StringUtils.isNotEmpty(dcpDoc.getSecurity())){
            dcpDoc.setSecurity(SearchSecurityEnum.getDescByCode(dcpDoc.getSecurity()));

        }
        return dcpDoc;
    }

}
