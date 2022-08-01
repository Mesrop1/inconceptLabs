package com.mesrop.bookstoreapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookNotFoundException extends RuntimeException {

	private String message;
}
