package com.example.twitter.clone.controller;

import com.example.twitter.clone.dto.AuthenticationResponse;
import com.example.twitter.clone.dto.LoginRequestDto;
import com.example.twitter.clone.dto.RefreshTokenRequest;
import com.example.twitter.clone.dto.RegisteredUserDto;
import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;

    /**
     * This end point is used for registering new user
     * AUTHORITY=ACCESSIBLE_TO_ALL
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisteredUserDto registeredUserDto){
        authService.signup(registeredUserDto);
        return new ResponseEntity<String>("User Registration Successful", HttpStatus.OK);
    }


    /**
     * This end point is used for registering new user
     * AUTHORITY=ACCESSIBLE_TO_ALL
     */
    @PostMapping("/signin")
    public AuthenticationResponse login(@RequestBody LoginRequestDto loginRequestDto){
        return authService.login(loginRequestDto);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        authService.deleteRefreshTokenForUser(refreshTokenRequest);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

}