package com.mesrop.bookstoreapp.persistance.repository;

import com.mesrop.bookstoreapp.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    void deleteById(Long id);

    void deleteAll();

    UserEntity findByUsername(String username);
}
