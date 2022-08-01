package com.mesrop.bookstoreapp.service.dto;

import com.mesrop.bookstoreapp.enums.Genre;
import com.mesrop.bookstoreapp.persistance.entity.BookEntity;
import lombok.*;
import org.hibernate.search.annotations.Field;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String isbn;

    private String title;

    private String year;

    private AuthorDto authorDto;

    private String smallImage;

    private String mediumImage;

    private String largeImage;

    private Genre genre;

    public static BookDto mapBookEntityToBookDto(BookEntity bookEntity) {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn(bookEntity.getIsbn());
        bookDto.setTitle(bookEntity.getTitle());
        bookDto.setYear(bookEntity.getYear());
        bookDto.setSmallImage(bookEntity.getSmallImage());
        bookDto.setMediumImage(bookEntity.getMediumImage());
        bookDto.setLargeImage(bookEntity.getLargeImage());
        bookDto.setGenre(bookEntity.getGenre());
        return bookDto;
    }

    public static List<BookDto> mapBookEntityToBookDto(List<BookEntity> bookEntities) {
        return bookEntities.stream().map(BookDto::mapBookEntityToBookDto).collect(Collectors.toList());
    }

    public static BookEntity mapBookDtoToBookEntity(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn(bookDto.getIsbn());
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setYear(bookDto.getYear());
        bookEntity.setSmallImage(bookDto.getSmallImage());
        bookEntity.setMediumImage(bookDto.getMediumImage());
        bookEntity.setLargeImage(bookDto.getLargeImage());
        bookEntity.setGenre(bookDto.getGenre());
        return bookEntity;
    }

}
