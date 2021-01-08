package com.example.twitter.clone.repository;

import com.example.twitter.clone.entity.Comment;
import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<List<Comment>> findByTweets(Tweets tweets);

    Optional<List<Comment>> findByUser(RegisteredUser user);
}
