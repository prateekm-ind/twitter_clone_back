package com.example.twitter.clone.service;

import com.example.twitter.clone.dto.AuthenticationResponse;
import com.example.twitter.clone.dto.LoginRequestDto;
import com.example.twitter.clone.dto.RegisteredUserDto;
import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.exception.TwitterAppGeneralException;
import com.example.twitter.clone.jwt.JwtProvider;
import com.example.twitter.clone.repository.RegisteredUserRepository;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AuthService {

    //@AllArgsConstructor will used for the constructor Injection
    //(@Autowired) feild injection substitute
    //final keyword ensure that this feild can be initialised only through constructor
    private final PasswordEncoder passwordEncoder;
    private final RegisteredUserRepository registeredUserRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProvider jwtProvider;

    //this annotation is used in method or class level when the specific class/method is used for Realtional DB interactions
    @Transactional
    public void signup(RegisteredUserDto registeredUserDto){
        RegisteredUser user= new RegisteredUser();
        user.setUsername(registeredUserDto.getUsername());
        user.setEmail(registeredUserDto.getEmail());

        //This has been done to ensure that the password is saved into the DB in encrypted format
        //Check the password encoder in the AuthService Class to know about the password-encryption method
        user.setPassword(passwordEncoder.encode(registeredUserDto.getPassword()));

        registeredUserRepository.save(user);

    }

    public AuthenticationResponse login(LoginRequestDto loginRequestDto){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e){
            throw new TwitterAppGeneralException("Credentials are not correct");
        }

        final UserDetails userDetails= userDetailsService.loadUserByUsername(loginRequestDto.getUsername());

        final String jwtToken= jwtProvider.generateToken(authentication);

        return new AuthenticationResponse(userDetails.getUsername(),jwtToken);
    }

    public RegisteredUser getCurrentUser() {
        UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return registeredUserRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
