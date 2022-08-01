package com.mesrop.bookstoreapp.controller;

import com.mesrop.bookstoreapp.exception.BookNotFoundException;
import com.mesrop.bookstoreapp.helper.CSVHelper;
import com.mesrop.bookstoreapp.helper.ResponseMessage;
import com.mesrop.bookstoreapp.criteria.BookPage;
import com.mesrop.bookstoreapp.criteria.BookSearchCriteria;
import com.mesrop.bookstoreapp.persistance.entity.BookEntity;
import com.mesrop.bookstoreapp.service.BookService;
import com.mesrop.bookstoreapp.service.dto.BookDto;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/parse/csv")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                bookService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

//    @GetMapping
//    public ResponseEntity<List<BookEntity>> getBooks(
//            @RequestParam(defaultValue = "0") Integer pageNumber,
//            @RequestParam(defaultValue = "10") Integer pageSize) {
//        List<BookEntity> list = bookService.getAllBooks(pageNumber, pageSize);
//        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        return bookService.getBook(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BookDto> delete(@PathVariable("id") Long id) {
        BookDto book = bookService.getBook(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable("id") Long id) throws IOException {
        String imageLocalPath = bookService.getImage(id);
        InputStream in = new BufferedInputStream(new FileInputStream(imageLocalPath));
        return IOUtils.toByteArray(in);
    }

    @GetMapping
    public ResponseEntity<List<BookEntity>> getBooks(BookPage bookPage, BookSearchCriteria bookSearchCriteria) {
        return new ResponseEntity<>(bookService.getBooks(bookPage, bookSearchCriteria),
                HttpStatus.OK);
    }
}
