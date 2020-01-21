package com.znamenacek.jakub.springBootSecurity.student.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super("Student not found.");
    }

    public NotFoundException(Integer id){
        super("Student with given id: "+id+" not found.");
    }
}
