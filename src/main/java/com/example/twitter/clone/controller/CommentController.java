package com.example.twitter.clone.controller;

import com.example.twitter.clone.dto.CommentDto;
import com.example.twitter.clone.service.AuthService;
import com.example.twitter.clone.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    Logger logger= LoggerFactory.getLogger(AuthController.class);
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
        logger.trace("inside create comment method");
        commentService.createComment(commentDto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/tweetid")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@RequestParam("tweetId") Long tweetId){
        logger.trace("inside get all comments for post method");
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByTweetId(tweetId));
    }

    @GetMapping("/username")
    public ResponseEntity<List<CommentDto>> getAllCommentByUser(@RequestParam("username") String username){
        logger.trace("inside get all comment by user method");
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByUsername(username));
    }

}
