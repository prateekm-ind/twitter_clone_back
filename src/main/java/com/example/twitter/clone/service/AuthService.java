package com.example.twitter.clone.service;

import com.example.twitter.clone.controller.AuthController;
import com.example.twitter.clone.dto.AuthenticationResponse;
import com.example.twitter.clone.dto.LoginRequestDto;
import com.example.twitter.clone.dto.RefreshTokenRequest;
import com.example.twitter.clone.dto.RegisteredUserDto;
import com.example.twitter.clone.entity.RefreshToken;
import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.exception.TwitterAppRuntimeException;
import com.example.twitter.clone.jwt.JwtProvider;
import com.example.twitter.clone.repository.RegisteredUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    Logger logger= LoggerFactory.getLogger(AuthService.class);

    //@AllArgsConstructor will used for the constructor Injection
    //(@Autowired) feild injection substitute
    //final keyword ensure that this feild can be initialised only through constructor
    private final PasswordEncoder passwordEncoder;
    private final RegisteredUserRepository registeredUserRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    //this annotation is used in method or class level when the specific class/method is used for Realtional DB interactions
    @Transactional
    public void signup(RegisteredUserDto registeredUserDto){
        logger.trace("inside signup method");
        RegisteredUser user= new RegisteredUser();
        user.setUsername(registeredUserDto.getUsername());
        user.setEmail(registeredUserDto.getEmail());

        //This has been done to ensure that the password is saved into the DB in encrypted format
        //Check the password encoder in the AuthService Class to know about the password-encryption method
        user.setPassword(passwordEncoder.encode(registeredUserDto.getPassword()));

        registeredUserRepository.save(user);

    }

    public AuthenticationResponse login(LoginRequestDto loginRequestDto){
        logger.trace("inside login method");
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e){
            logger.trace("inside catch block for BadCredentialException");
            throw new TwitterAppRuntimeException("Credentials are not correct");
        }

        final UserDetails userDetails= userDetailsService.loadUserByUsername(loginRequestDto.getUsername());

        final String jwtToken= jwtProvider.generateToken(authentication);

        final RefreshToken refreshToken= refreshTokenService.generateRefreshToken(loginRequestDto.getUsername());

        return new AuthenticationResponse(userDetails.getUsername(),jwtToken,refreshToken.getToken());
    }

    public RegisteredUser getCurrentUser() {
        logger.trace("inside getCurrentuser method");
        String username="";
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            username= ((UserDetails) principal).getUsername();
        }
        else{
            username= principal.toString();
        }

        return registeredUserRepository.findByUsername(username).orElseThrow(()-> new TwitterAppRuntimeException("User not found"));
    }

    public void deleteRefreshTokenForUser(RefreshTokenRequest refreshTokenRequest) {
        logger.trace("inside deleteRefreshTokenForUser method");
        refreshTokenService.deleteTokenFromRedisDb(refreshTokenRequest);
    }
}
