package com.akhianand.springrolejwt.es.controller;

import com.akhianand.springrolejwt.es.model.UserElk;
import com.akhianand.springrolejwt.es.service.SearchService;
import com.akhianand.springrolejwt.es.utils.ResultQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/query/{query}")
    public ResponseEntity<ResultQuery> searchQuery(@PathVariable String query) throws IOException {
        return new ResponseEntity<> (searchService.searchFromQuery(query.trim().toLowerCase()), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Iterable<UserElk>> findAll() throws IOException {
        return new ResponseEntity<> (searchService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public UserElk findById(@PathVariable Long id) throws IOException {
        return searchService.findById(id);
    }

    @GetMapping(" ")
    public List<UserElk> search(@RequestParam String name) throws IOException {
        return searchService.search(name);
    }


}
