package com.mesrop.bookstoreapp.service.dto;

import com.mesrop.bookstoreapp.persistance.entity.RatingEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private String rating;

    public static RatingDto mapRatingEntityToRatingDto(RatingEntity ratingEntity) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(ratingEntity.getRating());
        return ratingDto;
    }

    public static List<RatingDto> mapRatingEntityToRatingDto(List<RatingEntity> ratingEntities) {
        return ratingEntities.stream().map(RatingDto::mapRatingEntityToRatingDto).collect(Collectors.toList());
    }

    public static RatingEntity mapRatingDtoToRatingEntity(RatingDto ratingDto) {
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setRating(ratingDto.getRating());
        return ratingEntity;
    }
}
