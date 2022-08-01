package com.mesrop.bookstoreapp.service.dto;

import com.mesrop.bookstoreapp.persistance.entity.AddressEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String city;


    public static AddressDto mapAddressEntityToAddressDto(AddressEntity addressEntity) {
        AddressDto addressDto = new AddressDto();
        addressDto.setCity(addressEntity.getCity());
        return addressDto;
    }

    public static List<AddressDto> mapAddressEntityToAddressDto(List<AddressEntity> entities) {
        return entities.stream().map(AddressDto::mapAddressEntityToAddressDto).collect(Collectors.toList());
    }

    public static AddressEntity mapAddressDtoToAddressEntity(AddressDto addressDto) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(addressDto.getCity());
        return addressEntity;
    }
}
