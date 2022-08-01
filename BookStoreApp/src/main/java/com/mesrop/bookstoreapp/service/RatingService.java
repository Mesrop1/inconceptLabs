package com.mesrop.bookstoreapp.service;

import com.mesrop.bookstoreapp.helper.CSVHelper;
import com.mesrop.bookstoreapp.persistance.entity.RatingEntity;
import com.mesrop.bookstoreapp.persistance.entity.UserEntity;
import com.mesrop.bookstoreapp.persistance.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RatingService {
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    RatingRepository ratingRepository;

    public List<RatingEntity> save(MultipartFile file) throws IOException {
        List<RatingEntity> ratings = CSVHelper.parseRatings(file.getInputStream());
        return ratingRepository.saveAll(ratings).subList(0,100);
    }
}
