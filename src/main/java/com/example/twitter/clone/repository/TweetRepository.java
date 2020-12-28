package com.example.twitter.clone.repository;

import com.example.twitter.clone.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweets, Long> {
}
