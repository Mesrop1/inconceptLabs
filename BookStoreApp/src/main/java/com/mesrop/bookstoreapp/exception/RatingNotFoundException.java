package com.mesrop.bookstoreapp.exception;

public class RatingNotFoundException extends Exception {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
