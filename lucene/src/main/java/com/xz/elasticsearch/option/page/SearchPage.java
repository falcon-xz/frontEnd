package com.xz.elasticsearch.option.page;

import com.xz.elasticsearch.connect.ElasticsearchConnection;
import com.xz.elasticsearch.option.Parameter;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.searchafter.SearchAfterBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2019/1/28.
 */
public class SearchPage {

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

    @Test
    public void fromSize() {
        this.fromSize(0, 2);
    }

    private void fromSize(int from, int size) {
        System.out.println("--------------" + from);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.from(from);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.sort("_id");
        searchRequest.source(sourceBuilder);
        int count = fromSizeResult();
        if (count != 0) {
            fromSize(from + size, size);
        }
        release();
    }

    private int fromSizeResult() {
        int count = 0;
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            count = searchHits.length;
            for (SearchHit searchHit : searchHits) {
                System.out.println(searchHit.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 一般用于后台批处理 不能用于实时查询
     */
    @Test
    public void scroll() {
        String scrollId = null;
        long time = 2l;
        this.scroll(2, time, scrollId);
    }

    private void scroll(int size, long time, String scrollId) {
        SearchResponse searchResponse = null;
        if (scrollId == null) {
            //step1 初始化scroll 获得游标 cursor
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            sourceBuilder.size(size);
            sourceBuilder.sort("_id");
            searchRequest.source(sourceBuilder);
            //两分钟ES自动清理
            searchRequest.scroll(TimeValue.timeValueMinutes(time));
            try {
                searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //step2 循环游标
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(TimeValue.timeValueSeconds(time));
            try {
                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (scrollResult(searchResponse) > 0) {
            this.scroll(size, time, scrollId);
        } else {
            //step3 如果没有数据 清除scroll
            if (scrollId == null) {
                return;
            }
            ClearScrollRequest request = new ClearScrollRequest();
            request.addScrollId(scrollId);
            try {
                client.clearScroll(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        release();
    }

    private int scrollResult(SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        int count = searchHits.length;
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getId());
        }
        return count;
    }

    /**
     * 用于实时分页
     */
    @Test
    public void searchAfter() {
        String[] sortFields = new String[]{"price","_id"} ;
        this.searchAfter(2, sortFields,null);
    }

    private void searchAfter(int size, String[] sortFields,SearchSourceBuilder sourceBuilder) {
        if (sourceBuilder == null ){
            sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchAllQuery());
            sourceBuilder.size(size);
            for (String key : sortFields) {
                sourceBuilder.sort(key,SortOrder.DESC);
            }
            searchRequest.source(sourceBuilder);
        }
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            searchAfterResult(searchHits);
            if (searchHits.length == size) {
                //一定有分页
                sourceBuilder.searchAfter(searchHits[size-1].getSortValues()) ;
                System.out.println("------------");
                searchAfter(size,sortFields,sourceBuilder) ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        release();
    }

    private void searchAfterResult(SearchHit[] searchHits) {
        for (SearchHit searchHit : searchHits) {

            System.out.println(searchHit.getId()+"--"+searchHit.getSourceAsString());
        }
    }

    private void release() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
