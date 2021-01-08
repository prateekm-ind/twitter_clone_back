package com.example.twitter.clone.repository;

import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.entity.ThreadTweets;
import com.example.twitter.clone.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweets, Long> {
    List<Tweets> findAllByThreadTweets(ThreadTweets threadTweets);
    List<Tweets> findAllByUser(RegisteredUser user);
}
