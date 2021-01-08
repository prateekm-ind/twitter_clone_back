package com.example.twitter.clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshTokenRequest {
    private String username;
    @NotBlank
    private String refreshToken;

}
