package com.mesrop.bookstoreapp.controller;

import com.mesrop.bookstoreapp.exception.AuthorNotFoundException;
import com.mesrop.bookstoreapp.exception.UserNotFoundException;
import com.mesrop.bookstoreapp.helper.CSVHelper;
import com.mesrop.bookstoreapp.helper.ResponseMessage;
import com.mesrop.bookstoreapp.persistance.entity.RoleEntity;
import com.mesrop.bookstoreapp.persistance.entity.UserEntity;
import com.mesrop.bookstoreapp.service.UserService;
import com.mesrop.bookstoreapp.service.dto.UserDto;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    String message = "";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/parse/csv")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                userService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getUsers(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        List<UserEntity> list = userService.getAllUsers(pageNumber, pageSize);
        return new ResponseEntity<List<UserEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.getUser(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable("id") Long id) throws AuthorNotFoundException, UserNotFoundException {
        UserDto userDto = userService.getUser(id);
        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping()
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user) {
        return ResponseEntity.created(null).body(userService.saveUser(user));
    }
}
