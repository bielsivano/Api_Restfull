package com.br.users.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class CreateUserRequest {
    @NotBlank(message = "name is Required")
    private String name;
    @NotNull(message = "age is Required")
    private Integer age;

}
