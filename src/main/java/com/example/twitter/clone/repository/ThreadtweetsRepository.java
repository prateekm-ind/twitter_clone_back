package com.example.twitter.clone.repository;

import com.example.twitter.clone.entity.ThreadTweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThreadtweetsRepository extends JpaRepository<ThreadTweets, Long> {
    Optional<ThreadTweets> findByName(String name);
}
