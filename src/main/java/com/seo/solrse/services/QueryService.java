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

    /**
     * in this method we set the parameters of the query including the <b>q<b/>, the <b>rows</b>, and the <b>start</b>
     * to achieve the pagination of the application
     * @param query the query that the user type
     * @param page the 10<sup>th</sup>page
     * @return return list of the result from the sole server
     */
    public SolrDocumentList query(String query, int page) {
        System.out.println("inside the query method : \n" + query);
        final Map<String, String> queryParamMap = new HashMap<String, String>();
        queryParamMap.put("q", query);
        queryParamMap.put("rows", "10");
        queryParamMap.put("start", String.valueOf(11 * (page - 1)));
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);
        return getSolrClient(queryParams);
    }

    /**
     * This method make the connection with the solr server
     * @param queryParams the query parameters that we defined earlier
     * @return the results as solrDocs
     */
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


    /**
     *this method make the connection with the solr Core abd retrieve the result from it
     * @param queryParams the query
     * @param client2 the client
     * @return
     */
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
