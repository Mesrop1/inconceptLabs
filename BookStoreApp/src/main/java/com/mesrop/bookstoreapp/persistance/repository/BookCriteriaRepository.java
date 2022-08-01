package com.mesrop.bookstoreapp.persistance.repository;

import com.mesrop.bookstoreapp.criteria.BookPage;
import com.mesrop.bookstoreapp.criteria.BookSearchCriteria;
import com.mesrop.bookstoreapp.persistance.entity.BookEntity;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public BookCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<BookEntity> findAllWithFilters(BookPage bookPage, BookSearchCriteria bookSearchCriteria) {
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> bookRoot = criteriaQuery.from(BookEntity.class);
        Predicate predicate = getPredicate(bookSearchCriteria, bookRoot);
        criteriaQuery.where(predicate);
        setOrder(bookPage, criteriaQuery, bookRoot);

        TypedQuery<BookEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(bookPage.getPageNumber() * bookPage.getPageSize());
        typedQuery.setMaxResults(bookPage.getPageSize());

        Pageable pageable = getPageable(bookPage);

        long bookCount = getBookCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, bookCount);
    }

    private void setOrder(BookPage bookPage, CriteriaQuery<BookEntity> criteriaQuery,
                          Root<BookEntity> bookRoot) {
        if (bookPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(bookRoot.get(bookPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(bookRoot.get(bookPage.getSortBy())));
        }
    }

    private Predicate getPredicate(BookSearchCriteria bookSearchCriteria,
                                   Root<BookEntity> bookRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(bookSearchCriteria.getIsbn())) {
            predicates.add(
                    criteriaBuilder.like(bookRoot.get("isbn"),
                            "%" + bookSearchCriteria.getIsbn() + "%")
            );
        }
        if (Objects.nonNull(bookSearchCriteria.getTitle())) {
            predicates.add(
                    criteriaBuilder.like(bookRoot.get("title"),
                            "%" + bookSearchCriteria.getTitle() + "%")
            );
        }
        if (Objects.nonNull(bookSearchCriteria.getGenre())) {
            predicates.add(
                    criteriaBuilder.like(bookRoot.get("genre"),
                            "%" + bookSearchCriteria.getGenre() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Pageable getPageable(BookPage bookPage) {
        Sort sort = Sort.by(bookPage.getSortDirection(), bookPage.getSortBy());
        return PageRequest.of(bookPage.getPageNumber(), bookPage.getPageSize(), sort);
    }

    private long getBookCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<BookEntity> countRoot = countQuery.from(BookEntity.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
