package com.example.twitter.clone.controller;

import com.example.twitter.clone.dto.CommentDto;
import com.example.twitter.clone.service.AuthService;
import com.example.twitter.clone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/tweetid")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@RequestParam("tweetId") Long tweetId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByTweetId(tweetId));
    }

    @GetMapping("/username")
    public ResponseEntity<List<CommentDto>> getAllCommentByUser(@RequestParam("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByUsername(username));
    }

}
