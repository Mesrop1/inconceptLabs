package com.mesrop.bookstoreapp.persistance.repository;

import com.mesrop.bookstoreapp.persistance.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
