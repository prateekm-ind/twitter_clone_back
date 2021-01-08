package com.example.twitter.clone.service;

import com.example.twitter.clone.dto.CommentDto;
import com.example.twitter.clone.entity.Comment;
import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.entity.Tweets;
import com.example.twitter.clone.exception.TwitterAppRuntimeException;
import com.example.twitter.clone.repository.CommentRepository;
import com.example.twitter.clone.repository.RegisteredUserRepository;
import com.example.twitter.clone.repository.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final TweetRepository tweetRepository;
    private final RegisteredUserRepository registeredUserRepository;

    public void createComment(CommentDto commentDto) {
        Tweets tweets= tweetRepository.findById(commentDto.getTweetId()).orElseThrow(()-> new TwitterAppRuntimeException("Tweet not found Exception"));
        Comment comment= mapToEntity(commentDto , tweets, authService.getCurrentUser());
        commentRepository.save(comment);
    }

    public List<CommentDto> getAllCommentsByTweetId(Long tweetId) {
        Tweets tweets= tweetRepository.findById(tweetId).orElseThrow(()-> new TwitterAppRuntimeException("Tweet not found"));
        List<Comment> comments = commentRepository.findByTweets(tweets).orElseThrow(()-> new TwitterAppRuntimeException("No comments found"));
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder().id(comment.getId())
                .username(comment.getUser().getUsername())
                .createDate(comment.getCreatedDate())
                .tweetId(comment.getTweets().getTweetId())
                .text(comment.getText())
                .build();
    }

    public List<CommentDto> getCommentByUsername(String username) {
        RegisteredUser user= registeredUserRepository.findByUsername(username).orElseThrow(()->new TwitterAppRuntimeException("User not Found"));
        List<Comment> comments= commentRepository.findByUser(user).orElseThrow(()->new TwitterAppRuntimeException("No comments found for the user"));
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private Comment mapToEntity(CommentDto commentDto, Tweets tweets, RegisteredUser user){
        return Comment.builder().createdDate(Instant.now())
                    .user(user)
                    .tweets(tweets)
                    .text(commentDto.getText())
                    .build();
    }
}
