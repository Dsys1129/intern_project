package com.intern_project.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
@NonNull
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String name;
    private String gender;
    private String birthDate;
}
