package com.api.bookstore.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class IdFoundException extends EntityNotFoundException {

    public IdFoundException(Long id){
        super(String.format("Id ",id ," not found"));
    }
}
