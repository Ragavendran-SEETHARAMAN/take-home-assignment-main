package com.marionete.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class LoginRequestDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
