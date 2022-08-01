package com.mesrop.bookstoreapp.controller;

import com.mesrop.bookstoreapp.exception.AddressNotFoundException;
import com.mesrop.bookstoreapp.service.AddressService;
import com.mesrop.bookstoreapp.service.dto.AddressDto;
import com.mesrop.bookstoreapp.service.dto.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        try {
            List<AddressDto> addresses = addressService.getAllAddresses();
            if (addresses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AddressDto> delete(@PathVariable("id") Long id) throws AddressNotFoundException {
        AddressDto addressDto = addressService.getAddress(id);
        if (addressDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        addressService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
