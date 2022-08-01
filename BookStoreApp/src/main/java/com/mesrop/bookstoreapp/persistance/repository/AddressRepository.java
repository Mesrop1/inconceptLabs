package com.mesrop.bookstoreapp.persistance.repository;

import com.mesrop.bookstoreapp.persistance.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    void deleteById(Long id);

    void deleteAll();
}
