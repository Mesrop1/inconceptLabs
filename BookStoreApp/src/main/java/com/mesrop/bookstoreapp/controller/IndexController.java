package com.mesrop.bookstoreapp.controller;

import com.mesrop.bookstoreapp.service.SearchService;
import com.mesrop.bookstoreapp.service.dto.BookDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/books")
public class IndexController {
    private final SearchService searchService;

    public IndexController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/word")
    public List<BookDto> getBookByWord(@RequestParam String word) {
        return searchService.getBookBasedOnWord(word)
                .stream().map(BookDto::mapBookEntityToBookDto).collect(Collectors.toList());
    }
}
