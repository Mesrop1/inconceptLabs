package com.mesrop.bookstoreapp.persistance.repository;


import com.mesrop.bookstoreapp.persistance.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Long> {
}
