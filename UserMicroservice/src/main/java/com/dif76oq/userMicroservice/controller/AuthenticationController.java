package com.dif76oq.userMicroservice.controller;

import com.dif76oq.userMicroservice.dto.LoginUserDto;
import com.dif76oq.userMicroservice.dto.RegisterUserDto;
import com.dif76oq.userMicroservice.dto.ResendUserDto;
import com.dif76oq.userMicroservice.dto.VerifyUserDto;
import com.dif76oq.userMicroservice.model.User;
import com.dif76oq.userMicroservice.responses.LoginResponse;
import com.dif76oq.userMicroservice.service.impl.AuthenticationServiceImpl;
import com.dif76oq.userMicroservice.service.impl.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestBody ResendUserDto resendUserDto) {
        try {
            authenticationService.resendVerificationCode(resendUserDto);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
