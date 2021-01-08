package com.example.twitter.clone.service;

import com.example.twitter.clone.dto.RefreshTokenRequest;
import com.example.twitter.clone.entity.RefreshToken;
import com.example.twitter.clone.exception.TwitterAppRuntimeException;
import com.example.twitter.clone.repository.RefreshTokenImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenImpl refreshTokenRepository;

    public RefreshToken generateRefreshToken(String username) {
        RefreshToken refreshToken= new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUsername(username);
        refreshTokenRepository.add(refreshToken);

        return refreshToken;
    }

    public void deleteTokenFromRedisDb(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken token=refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken()).orElseThrow(()-> new TwitterAppRuntimeException("Refresh Token not found"));
        refreshTokenRepository.delete(token);
    }
}
