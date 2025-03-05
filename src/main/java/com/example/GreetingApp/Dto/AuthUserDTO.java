package com.example.GreetingApp.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthUserDTO {
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "First letter must be uppercase")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "First letter must be uppercase")
    private String lastName;

    @Email
    private String email;

    private String password;
}

