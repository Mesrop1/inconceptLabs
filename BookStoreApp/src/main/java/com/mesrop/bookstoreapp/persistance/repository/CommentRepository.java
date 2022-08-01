package com.mesrop.bookstoreapp.persistance.repository;

import com.mesrop.bookstoreapp.persistance.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
