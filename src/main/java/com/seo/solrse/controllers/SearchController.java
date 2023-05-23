package com.seo.solrse.controllers;

import com.seo.solrse.services.AlkhalilService;
import com.seo.solrse.services.QueryService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {
    private final QueryService service;
    private final AlkhalilService khalil;

    @Value("${spring.datasource.url}")
    private String mysql;

    @Autowired
    public SearchController(QueryService service, AlkhalilService khalil) {
        this.service = service;
        this.khalil = khalil;
    }

    @PostMapping(path = "/")
    public ResponseEntity<SolrDocumentList> getQuery(@RequestBody Map<String,String> queryParameter) throws IOException, ParserConfigurationException, SAXException {
        String query = queryParameter.get("query");
        int page = Integer.parseInt(queryParameter.get("page"));
        query = khalil.convertToLemmas(query);

        SolrDocumentList entries = service.query(query,page);
        long docs = entries.getNumFound();
        long pages = entries.getNumFound()/10;
        SolrDocument doc = new SolrDocument();
        doc.addField("page",pages);
        doc.addField("docs",docs);
        entries.add(doc);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("docs", String.valueOf(docs));
        httpHeaders.add("pages", String.valueOf(pages));
        return new ResponseEntity<>(entries,httpHeaders,HttpStatus.OK);
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

    @GetMapping(path = "/khalil/{query}")
    public String khalilTest(@PathVariable String query) throws IOException, ParserConfigurationException, SAXException {
        return khalil.convertToLemmas(query);
    }


}
