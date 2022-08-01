package com.mesrop.bookstoreapp.controller;

import com.mesrop.bookstoreapp.exception.AuthorNotFoundException;
import com.mesrop.bookstoreapp.service.AuthorService;
import com.mesrop.bookstoreapp.service.dto.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") Long id) throws AuthorNotFoundException {
        return authorService.getAuthor(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AuthorDto> delete(@PathVariable("id") Long id) throws AuthorNotFoundException {
        AuthorDto authorDto = authorService.getAuthor(id);
        if (authorDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
