package com.mesrop.bookstoreapp.service;

import com.mesrop.bookstoreapp.exception.PublisherNotFoundException;
import com.mesrop.bookstoreapp.persistance.entity.PublisherEntity;
import com.mesrop.bookstoreapp.persistance.repository.PublisherRepository;
import com.mesrop.bookstoreapp.service.dto.PublisherDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {


    PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherDto> getAllPublishers() {
        return PublisherDto.mapToPublisherEntityToPublisherDto(publisherRepository.findAll());
    }

    public PublisherDto getPublisher(Long id) throws PublisherNotFoundException {
        PublisherEntity publisherEntity = publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException());
        return PublisherDto.mapToPublisherEntityToPublisherDto(publisherEntity);
    }

    public void delete(Long id) {
        publisherRepository.deleteById(id);
    }
}
