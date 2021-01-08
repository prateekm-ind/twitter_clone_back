package com.example.twitter.clone.controller;


import com.example.twitter.clone.dto.TweetResponseDto;
import com.example.twitter.clone.dto.TweetsRequestDto;
import com.example.twitter.clone.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/tweet")
@AllArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetResponseDto> createTweet(@RequestBody TweetsRequestDto tweetsRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.createTweet(tweetsRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<TweetResponseDto>> getAllTweets(){
        ResponseEntity<List<TweetResponseDto>> body = status(HttpStatus.OK).body(tweetService.getAllTweets());
        return body;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseDto> getTweet(@PathVariable Long id){
        return status(HttpStatus.OK).body(tweetService.getTweet(id));
    }

    @GetMapping("by-retweet/{id}")
    public ResponseEntity<List<TweetResponseDto>> getPostsBySubreddit(@PathVariable Long id) {
        return status(HttpStatus.OK).body(tweetService.getPostsByThreadTweets(id));
    }

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<TweetResponseDto>> getTweetsByUsername(@PathVariable String username){
        return status(HttpStatus.OK).body(tweetService.getTweetsByUsername(username));
    }

}
