package com.mesrop.bookstoreapp.persistance.repository;

import com.mesrop.bookstoreapp.enums.Genre;
import com.mesrop.bookstoreapp.persistance.entity.AuthorEntity;
import com.mesrop.bookstoreapp.persistance.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    void deleteById(Long id);

    void deleteAll();

    @Query(value = "select large_image from book", nativeQuery = true)
    List<String> findImages();

    @Query("SELECT b FROM BookEntity b WHERE b.status IS NULL")
    List<BookEntity> findByLargeImage();

    List<BookEntity> findByGenre(Genre genre);

    boolean existsByIsbn(String isbn);

    @Query(
            "SELECT b FROM BookEntity b " +
                    "INNER JOIN b.author a " +
                    "WHERE (:bookTitle = '' OR b.title LIKE CONCAT(:bookTitle, '%')) " +
                    "AND a.name LIKE CONCAT(:authorName, '%') "
                    //"AND b.genre = :genre"
    )
    Page<BookEntity> findBooksByCriteria(@Param("bookTitle") String bookTitle,
                                         @Param("authorName") String authorName,
                                         Pageable pageable);
}
