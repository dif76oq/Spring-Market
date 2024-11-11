package com.dif76oq.userMicroservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserDto {
    private String email;
    private String password;
    private String username;

}
