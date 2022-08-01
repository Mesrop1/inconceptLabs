package com.mesrop.bookstoreapp.controller;

import com.mesrop.bookstoreapp.exception.PublisherNotFoundException;
import com.mesrop.bookstoreapp.service.PublisherService;
import com.mesrop.bookstoreapp.service.dto.PublisherDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/publishers")
public class PublisherController {

    private final PublisherService publisherService;
    String message = "";

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping()
    public ResponseEntity<List<PublisherDto>> getAllPublishers() {
        try {
            List<PublisherDto> publisherDtos = publisherService.getAllPublishers();
            if (publisherDtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(publisherDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public PublisherDto getPublisherById(@PathVariable("id") Long id) throws PublisherNotFoundException {
        return publisherService.getPublisher(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PublisherDto> delete(@PathVariable("id") Long id) throws PublisherNotFoundException {
        PublisherDto publisherDto = publisherService.getPublisher(id);
        if (publisherDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        publisherService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
