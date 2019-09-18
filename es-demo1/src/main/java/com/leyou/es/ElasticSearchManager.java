package com.leyou.es;

import org.apache.lucene.search.FuzzyQuery;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

public class ElasticSearchManager {
    TransportClient client = null;
    @Before
    public void initClient()throws Exception{
        client = new PreBuiltTransportClient(Settings.EMPTY)
         .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9300));

    }
    @Test
    public void testQuery(){
//        QueryBuilder qb = QueryBuilders.termQuery("goodsName","小米"); //构建term查询
//        QueryBuilder qb = QueryBuilders.fuzzyQuery("goodsName","appla"); //构建模糊(容错)查询
//        QueryBuilder qb = QueryBuilders.wildcardQuery("goodsName","*小*"); //构建通配符查询
//        QueryBuilder qb = QueryBuilders.matchQuery("goodsName","小米手机"); //构建分词查询
//        QueryBuilder qb = QueryBuilders.matchAllQuery(); //构建查询所有
//        FuzzyQueryBuilder qb = QueryBuilders.fuzzyQuery("goodsName","花为"); //构建模糊(容错)查询
//        qb.fuzziness(Fuzziness.ONE); //设置偏离度.
//    QueryBuilder qb  =QueryBuilders.rangeQuery("price").gte(2000).lte(4000); //区间查询
    QueryBuilder qb = QueryBuilders.boolQuery()
     .must(QueryBuilders.termQuery("goodsName","小米"))
     .mustNot(QueryBuilders.rangeQuery("price").gte(2000).lte(4000));  // 组合查询

        SearchResponse searchResponse = client.prepareSearch("heima") //    执行查询的索引库
         .setQuery(qb)  // 把构建的term查询放入到请求中生效
         .setSize(100).get(); //执行
        long totalHits = searchResponse.getHits().getTotalHits();
        System.out.println("总数为:"+totalHits);
        SearchHit[] searchHit = searchResponse.getHits().getHits();
        for (SearchHit documentFields : searchHit) {
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }
    @After
    public void end(){
        client.close();
    }
 @After
    public void zhangsan(){
        System.out.println("张三")
    }
}
