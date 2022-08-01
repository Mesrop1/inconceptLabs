package com.mesrop.bookstoreapp.service.dto;

import com.mesrop.bookstoreapp.persistance.entity.PublisherEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDto {

    private String name;

    public static PublisherDto mapToPublisherEntityToPublisherDto(PublisherEntity publisherEntity) {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setName(publisherEntity.getName());
        return publisherDto;
    }

    public static List<PublisherDto> mapToPublisherEntityToPublisherDto(List<PublisherEntity> publisherEntities) {
        return publisherEntities.stream().map(PublisherDto::mapToPublisherEntityToPublisherDto).collect(Collectors.toList());
    }

    public static PublisherEntity mapToPublisherDtoToPublisherEntity(PublisherDto publisherDto) {
        PublisherEntity publisherEntity = new PublisherEntity();
        publisherEntity.setName(publisherDto.getName());
        return publisherEntity;
    }
}
