package com.br.users.dto;

import lombok.Data;

@Data
public class FieldError {
    private String field;
    private String messege;

    public FieldError(String field, String messege) {
        this.field = field;
        this.messege = messege;
    }
}
