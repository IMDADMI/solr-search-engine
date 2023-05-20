package com.seo.solrse.controllers;

import com.seo.solrse.services.QueryService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {
    private final QueryService service;
    @Value("${spring.datasource.url}")
    private String mysql;

    @Autowired
    public SearchController(QueryService service) {
        this.service = service;
    }

    @PostMapping(path = "/")
    public SolrDocumentList getQuery(@RequestBody Map<String,String> queryParameter) throws SolrServerException, IOException {
        String query = queryParameter.get("query");
        int page = Integer.parseInt(queryParameter.get("page"));
        System.out.println("sss");
        return service.query(query,page);
    }

    @GetMapping(path = "/{query}")
    public SolrDocumentList getTest(@PathVariable String query)  {
        return service.query(query,1);
    }
    @GetMapping(path = "")
    public SolrDocumentList getTest()  {
        System.out.println("the value of sql url is : "+this.mysql);
        return service.query("*:*",1);
    }


}
