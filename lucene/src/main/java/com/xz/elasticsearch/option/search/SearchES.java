package com.xz.elasticsearch.option.search;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import com.xz.elasticsearch.option.Parameter;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2019/1/4.
 */
public class SearchES {
    private RestHighLevelClient client;
    private SearchRequest searchRequest;

    @Before
    public void before() {
        client = ElasticsearchConnection.getConnection();
        searchRequest = new SearchRequest(Parameter.ik_index);
        searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen());
        //本地搜索优先
        searchRequest.preference("_local");
    }

    /**
     * TermQuery 一个查询相匹配的文件包含一个术语
     * standard  中文只能字 英文只能 准确的词
     * ik 正常
     */
    @Test
    public void TermQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("content", "近视镜"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 查询匹配所有文件
     */
    @Test
    public void matchAllQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.sort("createDate", SortOrder.DESC);
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 类似先分词(找常用词)  取词频  先匹配高频  再匹配低频
     */
    @Test
    public void commonTermsQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.commonTermsQuery("content", "定制近视镜"));
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.sort("createDate", SortOrder.DESC);
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 相同类型的分词器 字段中包含 text 的查询  或者关系
     */
    @Test
    public void multiMatchQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.multiMatchQuery("蓝光", "content", "name"));
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 分词完后  不光分词要匹配   分词次序 必须一致才能查询
     */
    @Test
    public void matchPhraseQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("content", "防蓝光"));
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }


    @Test
    public void matchPhrasePrefixQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery("content", "防蓝光眼"));
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 子查询的结果做union
     */
    @Test
    public void disMaxQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        disMaxQueryBuilder.add(QueryBuilders.termQuery("price", 35.7));
        disMaxQueryBuilder.add(QueryBuilders.termQuery("_id", 3));
        sourceBuilder.query(disMaxQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.sort("price", SortOrder.DESC);
        sourceBuilder.trackScores(true);
        searchRequest.source(sourceBuilder);
        result();
    }

    @Test
    public void idsQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery();
        idsQueryBuilder.addIds("1","2");
        sourceBuilder.query(idsQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 模糊查询
     */
    @Test
    public void fuzzyQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("content","近视镜");
        sourceBuilder.query(fuzzyQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 前缀匹配  中文符号 需要处理否则无法查询
     */
    @Test
    public void prefixQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("content","小米");
        sourceBuilder.query(prefixQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 范围查询
     */
    @Test
    public void rangeQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price");
        rangeQueryBuilder.from(50).to(100);
        sourceBuilder.query(rangeQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 配符查询，支持* 任意字符串；？任意一个字符
     *      中文英文不一样
     */
    @Test
    public void wildcardQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("content","*立方*");
        sourceBuilder.query(wildcardQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     * 全字段检索  包含字段权重等信息
     */
    @Test
    public void queryStringQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("眼镜");
        queryStringQueryBuilder.analyzer("ik_max_word");
        queryStringQueryBuilder.field("name",1) ;
        queryStringQueryBuilder.field("content",2) ;
        sourceBuilder.query(queryStringQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    /**
     *
     */
    @Test
    public void boolQueryBuilder() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must( new TermQueryBuilder("content","眼镜")).should(new TermQueryBuilder("content","辐射"));

        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(3);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        result();
    }

    private void result() {
        System.out.println("rest:"+searchRequest.source().toString());
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("检索失败的索引分片量：" + searchResponse.getFailedShards());
            System.out.println("检索跳过的索引分片量：" + searchResponse.getSkippedShards());
            System.out.println("检索成功的索引分片量：" + searchResponse.getSuccessfulShards());
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            System.out.println("hits数量：" + searchHits.length);
            System.out.println("---------------------------------------------");
            for (SearchHit searchHit : searchHits) {
                System.out.println("index名称：" + searchHit.getIndex());
                System.out.println("type名称：" + searchHit.getType());
                System.out.println(searchHit.getSourceAsString());
                StringBuilder sb = new StringBuilder();
                for (String matched :
                        searchHit.getMatchedQueries()) {
                    sb.append(matched).append(",");
                }
                System.out.println("MatchedQueries:" + sb.toString());

                System.out.println("id：" + searchHit.getId());
                System.out.println("权重：" + searchHit.getScore());
                sb = new StringBuilder();
                for (Map.Entry<String, DocumentField> entry :
                        searchHit.getFields().entrySet()) {
                    sb.append(entry.getKey()).append(",");
                }
                System.out.println("字段：" + sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
