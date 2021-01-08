package com.example.twitter.clone.repository;

import com.example.twitter.clone.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RefreshTokenRepository {
    void add(RefreshToken refreshToken);
    void delete(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
}
