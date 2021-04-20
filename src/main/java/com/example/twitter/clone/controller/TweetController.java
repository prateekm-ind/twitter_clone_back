package com.example.twitter.clone.controller;


import com.example.twitter.clone.dto.TweetResponseDto;
import com.example.twitter.clone.dto.TweetsRequestDto;
import com.example.twitter.clone.service.TweetService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/tweet")
@RequiredArgsConstructor
public class TweetController {
    Logger logger= LoggerFactory.getLogger(TweetController.class);

    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetResponseDto> createTweet(@RequestBody TweetsRequestDto tweetsRequestDto){
        logger.trace("inside createTweet method");
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.createTweet(tweetsRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<TweetResponseDto>> getAllTweets(){
        logger.trace("inside getAllTweets method");
        ResponseEntity<List<TweetResponseDto>> body = status(HttpStatus.OK).body(tweetService.getAllTweets());
        return body;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseDto> getTweet(@PathVariable Long id){
        logger.trace("inside getTweet method");
        return status(HttpStatus.OK).body(tweetService.getTweet(id));
    }

    @GetMapping("by-retweet/{id}")
    public ResponseEntity<List<TweetResponseDto>> getTweetsByThreadTweets(@PathVariable Long id) {
        logger.trace("inside getTweetsByThreadTweets method");
        return status(HttpStatus.OK).body(tweetService.getPostsByThreadTweets(id));
    }

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<TweetResponseDto>> getTweetsByUsername(@PathVariable String username){
        logger.trace("inside getTweetsByUsername method");
        return status(HttpStatus.OK).body(tweetService.getTweetsByUsername(username));
    }

}
