package com.mesrop.bookstoreapp;

import com.mesrop.bookstoreapp.persistance.entity.RoleEntity;
import com.mesrop.bookstoreapp.persistance.entity.UserEntity;
import com.mesrop.bookstoreapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
public class BookStoreAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreAppApplication.class, args);
    }

    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public CommandLineRunner commandLineRunner(UserService userService) {
        return (args) -> {
            userService.saveRole(new RoleEntity(null, "ROLE_USER"));
            userService.saveRole(new RoleEntity(null, "ROLE_ADMIN"));
            userService.saveRole(new RoleEntity(null, "ROLE_EDITOR"));

//            userService.saveUser(new UserEntity(null, 10, "Mesrop", "1234", new ArrayList<>(), null, null, null, null));
//            userService.saveUser(new UserEntity(null, 25, "Hakob", "555", new ArrayList<>(), null, null, null, null));
//            userService.saveUser(new UserEntity(null, 30, "Karen", "666", new ArrayList<>(), null, null, null, null));

            userService.addRoleToUser("Mesrop", "ROLE_ADMIN");
            userService.addRoleToUser("Hakob", "ROLE_USER");
            userService.addRoleToUser("Karen", "ROLE_EDITOR");
        };
    }
}
