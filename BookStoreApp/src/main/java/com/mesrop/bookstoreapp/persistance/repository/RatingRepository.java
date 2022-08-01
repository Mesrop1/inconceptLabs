package com.mesrop.bookstoreapp.persistance.repository;


import com.mesrop.bookstoreapp.persistance.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

}
