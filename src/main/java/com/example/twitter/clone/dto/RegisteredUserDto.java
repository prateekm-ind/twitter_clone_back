package com.example.twitter.clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisteredUserDto {
    private String username;
    private String email;
    private String password;
}
