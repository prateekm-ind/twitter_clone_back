package com.example.twitter.clone.repository;

import com.example.twitter.clone.entity.ReTweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetweetsRepository extends JpaRepository<ReTweets, Long> {
}
