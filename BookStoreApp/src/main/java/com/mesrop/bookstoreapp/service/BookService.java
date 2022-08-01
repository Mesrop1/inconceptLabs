package com.mesrop.bookstoreapp.service;

import com.mesrop.bookstoreapp.criteria.BookPage;
import com.mesrop.bookstoreapp.criteria.BookSearchCriteria;
import com.mesrop.bookstoreapp.enums.Genre;
import com.mesrop.bookstoreapp.exception.BookNotFoundException;
import com.mesrop.bookstoreapp.helper.CSVHelper;
import com.mesrop.bookstoreapp.persistance.entity.BookEntity;
import com.mesrop.bookstoreapp.persistance.repository.BookCriteriaRepository;
import com.mesrop.bookstoreapp.persistance.repository.BookRepository;
import com.mesrop.bookstoreapp.service.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookCriteriaRepository bookCriteriaRepository;
    private final CSVHelper csvHelper;

    public BookService(BookRepository bookRepository, BookCriteriaRepository bookCriteriaRepository, CSVHelper csvHelper) {
        this.bookRepository = bookRepository;
        this.bookCriteriaRepository = bookCriteriaRepository;
        this.csvHelper = csvHelper;
    }

    public List<BookEntity> save(MultipartFile file) throws IOException {
        List<BookEntity> books = csvHelper.parseBooks(file.getInputStream());
        return bookRepository.saveAll(books.subList(0,10000));
    }

    public List<BookEntity> getAllBooks(int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<BookEntity> pagedResult = bookRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public BookDto getBook(Long id) throws BookNotFoundException {
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return BookDto.mapBookEntityToBookDto(bookEntity);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public String getImage(Long id) {
        return bookRepository.findById(id).get().getFilePath();
    }

    public BookEntity updateBook(Long id, BookEntity book) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookEntity.setFilePath(book.getFilePath());
        bookEntity.setStatus(book.getStatus());
        bookEntity.setIsbn(book.getIsbn());
        return bookRepository.save(bookEntity);
    }

    public List<BookEntity> getBooks(BookPage page, BookSearchCriteria bookSearchCriteria) {
        return bookRepository.findBooksByCriteria(
                bookSearchCriteria.getTitle() == null ? "" : bookSearchCriteria.getTitle(),
                //bookSearchCriteria.getAuthor().getName() == null ? "" : bookSearchCriteria.getAuthor().getName(),
                bookSearchCriteria.getIsbn() == null ? "" : bookSearchCriteria.getIsbn(),
                PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(page.getSortBy()))).getContent();
    }
}
