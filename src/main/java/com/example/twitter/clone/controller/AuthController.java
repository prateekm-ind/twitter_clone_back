package com.example.twitter.clone.controller;

import com.example.twitter.clone.dto.AuthenticationResponse;
import com.example.twitter.clone.dto.LoginRequestDto;
import com.example.twitter.clone.dto.RefreshTokenRequest;
import com.example.twitter.clone.dto.RegisteredUserDto;
import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    Logger logger= LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    /**
     * This end point is used for registering new user
     * AUTHORITY=ACCESSIBLE_TO_ALL
     */
    //@CrossOrigin(origins = "http://localhost:4200/**")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisteredUserDto registeredUserDto){
        logger.trace("inside signup method");
        authService.signup(registeredUserDto);
        return new ResponseEntity<String>("User Registration Successful", HttpStatus.OK);
    }


    /**
     * This end point is used for registering new user
     * AUTHORITY=ACCESSIBLE_TO_ALL
     */
    @PostMapping("/signin")
    public AuthenticationResponse login(@RequestBody LoginRequestDto loginRequestDto){
        logger.trace("inside signin method");
        return authService.login(loginRequestDto);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        logger.trace("inside logout method");
        authService.deleteRefreshTokenForUser(refreshTokenRequest);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

}