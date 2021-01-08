package com.example.twitter.clone.repository;

import com.example.twitter.clone.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Repository
public class RefreshTokenImpl implements RefreshTokenRepository{
    private static final String KEY="USER";
    @Autowired
    private RedisTemplate<String, RefreshToken> redisTemplate;
    private HashOperations hashOperations;

    /*@Autowired
    public RefreshTokenImpl(RedisTemplate<String, RefreshToken> redisTemplate){
        this.redisTemplate=redisTemplate;
    }*/

    @PostConstruct
    private void init(){
        hashOperations=redisTemplate.opsForHash();
    }

    @Override
    public void add(RefreshToken refreshToken) {
        hashOperations.put(KEY, refreshToken.getUsername(),refreshToken.getToken());
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        hashOperations.delete(KEY,refreshToken.getToken());
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return (Optional<RefreshToken>) hashOperations.get(KEY,token);
    }
}
