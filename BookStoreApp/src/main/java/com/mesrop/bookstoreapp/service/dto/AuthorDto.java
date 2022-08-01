package com.mesrop.bookstoreapp.service.dto;

import com.mesrop.bookstoreapp.persistance.entity.AuthorEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private String name;

    private BookDto bookDto;

    public static AuthorDto mapAuthorEntityToAuthorDto(AuthorEntity authorEntity) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(authorEntity.getName());
        return authorDto;
    }

    public static List<AuthorDto> mapAuthorEntityToAuthorDto(List<AuthorEntity> entities) {
        return entities.stream().map(AuthorDto::mapAuthorEntityToAuthorDto).collect(Collectors.toList());
    }

    public static AuthorEntity mapAuthorDtoToAuthorEntity(AuthorDto authorDto) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(authorDto.getName());
        return authorEntity;
    }
}
