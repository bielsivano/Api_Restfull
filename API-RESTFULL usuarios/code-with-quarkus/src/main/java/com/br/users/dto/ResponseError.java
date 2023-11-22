package com.br.users.dto;

import io.netty.channel.unix.Errors;
import jakarta.validation.ConstraintViolation;
import org.eclipse.microprofile.reactive.streams.operators.spi.Stage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



public class ResponseError {
    private String messege;

    public ResponseError(String messege, Collection<FieldError> errors) {
        this.messege = messege;
        this.errors = errors;
    }

    public String getMessege() {
        return messege;
    }
    public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>>violations){
        List <FieldError> errors =violations
                .stream().map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        String messege = "Validaton error";

        var responseError = new ResponseError(messege, errors);
        return responseError;


    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public Collection<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(Collection<FieldError> errors) {
        this.errors = errors;
    }

    private Collection<FieldError> errors;
}
