package com.mesrop.bookstoreapp.service;

import com.mesrop.bookstoreapp.exception.AddressNotFoundException;
import com.mesrop.bookstoreapp.exception.BookNotFoundException;
import com.mesrop.bookstoreapp.persistance.entity.AddressEntity;
import com.mesrop.bookstoreapp.persistance.entity.BookEntity;
import com.mesrop.bookstoreapp.persistance.repository.AddressRepository;
import com.mesrop.bookstoreapp.service.dto.AddressDto;
import com.mesrop.bookstoreapp.service.dto.BookDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AddressService {

    AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDto> getAllAddresses() {
        return AddressDto.mapAddressEntityToAddressDto(addressRepository.findAll());
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    public AddressDto getAddress(Long id) throws AddressNotFoundException {
        AddressEntity address = addressRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return AddressDto.mapAddressEntityToAddressDto(address);
    }
}
