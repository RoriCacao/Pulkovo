package ru.sultanov.pulkovo.pulkovo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PersonDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
