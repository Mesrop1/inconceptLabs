package com.mesrop.bookstoreapp.service;

import com.mesrop.bookstoreapp.exception.AuthorNotFoundException;
import com.mesrop.bookstoreapp.exception.BookNotFoundException;
import com.mesrop.bookstoreapp.persistance.entity.AuthorEntity;
import com.mesrop.bookstoreapp.persistance.repository.AuthorRepository;
import com.mesrop.bookstoreapp.service.dto.AuthorDto;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorEntity save(AuthorEntity author) {
        return authorRepository.save(author);
    }

    public AuthorDto getAuthor(Long id) throws AuthorNotFoundException {
        AuthorEntity author = authorRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return AuthorDto.mapAuthorEntityToAuthorDto(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
