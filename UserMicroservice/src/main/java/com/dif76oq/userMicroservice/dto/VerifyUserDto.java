package com.dif76oq.userMicroservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyUserDto {
    private String email;
    private String verificationCode;
}