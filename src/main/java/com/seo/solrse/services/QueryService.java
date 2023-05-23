package com.seo.solrse.services;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryService {

    public SolrDocumentList query(String query, int page) {
        System.out.println("inside the query method : \n" + query);
        final Map<String, String> queryParamMap = new HashMap<String, String>();
        queryParamMap.put("q", query);
        queryParamMap.put("rows", "10");
        queryParamMap.put("start", String.valueOf(11 * (page - 1)));
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);
        return getSolrClient(queryParams);
    }

    public SolrDocumentList getSolrClient(MapSolrParams queryParams) {
        // solr-container is the name of solr container
        final String solrUrl = "http://solr-container:8983/solr";
//         final String solrUrl = "http://localhost:8983/solr";
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        return getDoc(queryParams, client);
    }

    private SolrDocumentList getDoc(MapSolrParams queryParams, HttpSolrClient client2) {
        try {
            QueryResponse response = client2.query("secCore", queryParams);
//            QueryResponse response = client2.query("secCore", queryParams);
            final SolrDocumentList documents = response.getResults();
            return documents;

        } catch (SolrServerException | IOException e) {
            System.out.println("error \n" + e.getMessage());
            return null;
        }
    }

}
