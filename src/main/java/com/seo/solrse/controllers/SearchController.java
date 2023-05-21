package com.seo.solrse.controllers;

import com.seo.solrse.services.QueryService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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
    public ResponseEntity<SolrDocumentList> getQuery(@RequestBody Map<String,String> queryParameter) throws SolrServerException, IOException {
        String query = queryParameter.get("query");
        int page = Integer.parseInt(queryParameter.get("page"));
        System.out.println("sss");
        SolrDocumentList entries = service.query(query,page);
        long docs = entries.getNumFound();
        long pages = entries.getNumFound()/10;
        HttpHeaders httpHeaders = new HttpHeaders();
        System.out.println("docs are : "+docs);
        httpHeaders.add("docs", String.valueOf(docs));
        httpHeaders.add("pages", String.valueOf(pages));
        return new ResponseEntity<>(entries,httpHeaders,HttpStatus.OK);
    }

    @GetMapping(path = "/{query}/{page}")
    public SolrDocumentList getTest(@PathVariable String query, @PathVariable String page)  {
        return service.query(query,Integer.parseInt(page));
    }
    @GetMapping(path = "")
    public SolrDocumentList getAll()  {
        System.out.println("the value of sql url is : "+this.mysql);
        SolrDocumentList entries = service.query("*:*",1);
        long docs = entries.getNumFound();
        long pages = entries.getNumFound()/10;
        System.out.println("docs are : "+docs);
        return entries;
    }


}
