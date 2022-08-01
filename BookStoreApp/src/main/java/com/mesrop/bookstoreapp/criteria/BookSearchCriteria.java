package com.mesrop.bookstoreapp.criteria;

import com.mesrop.bookstoreapp.persistance.entity.AuthorEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchCriteria {

    private AuthorEntity author;
    private String isbn;
    private String title;
    private String genre;
}
