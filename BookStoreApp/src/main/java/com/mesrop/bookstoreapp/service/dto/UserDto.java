package com.mesrop.bookstoreapp.service.dto;

import com.mesrop.bookstoreapp.persistance.entity.UserEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String password;

    public static UserDto mapUserEntityToUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUsername(userEntity.getUsername());
        userDto.setPassword(userEntity.getPassword());
        return userDto;
    }

    public static List<UserDto> mapUserEntityToUserDto(List<UserEntity> entities) {
        return entities.stream().map(UserDto::mapUserEntityToUserDto).collect(Collectors.toList());
    }

    public static UserEntity mapUserDtoToUserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        return userEntity;
    }
}
