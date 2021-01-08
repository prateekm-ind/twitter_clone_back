package com.example.twitter.clone.service;

import com.example.twitter.clone.dto.TweetResponseDto;
import com.example.twitter.clone.dto.TweetsRequestDto;
import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.entity.ThreadTweets;
import com.example.twitter.clone.entity.Tweets;
import com.example.twitter.clone.exception.TwitterAppRuntimeException;
import com.example.twitter.clone.repository.RegisteredUserRepository;
import com.example.twitter.clone.repository.ThreadtweetsRepository;
import com.example.twitter.clone.repository.TweetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class TweetService {

    //private final TweetsRequestDto tweetsRequestDto;
    private final TweetRepository tweetRepository;
    private final AuthService authService;
    private final ThreadtweetsRepository threadtweetsRepository;
    private final RegisteredUserRepository registeredUserRepository;

    @Transactional
    public TweetResponseDto createTweet(TweetsRequestDto tweetsRequestDto){
        ThreadTweets threadTweets = threadtweetsRepository.findByName(tweetsRequestDto.getRetweetname()).orElseThrow(()->new TwitterAppRuntimeException("Thread not Found exception"));
        Tweets tweets= mapToEntity(tweetsRequestDto, threadTweets, authService.getCurrentUser());
        tweetRepository.save(tweets);
        return mapToResponseDto(tweets);
    }

    @Transactional(readOnly = true)
    public List<TweetResponseDto> getAllTweets() {
        return tweetRepository.findAll().stream().map(this::mapToResponseDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public TweetResponseDto getTweet(Long id) {
        return mapToResponseDto(tweetRepository.findById(id).orElseThrow(() -> new TwitterAppRuntimeException("Tweet Not Found")));

    }

    @Transactional(readOnly = true)
    public List<TweetResponseDto> getPostsByThreadTweets(Long id) {
        ThreadTweets threadTweets= threadtweetsRepository.findById(id).orElseThrow(()-> new TwitterAppRuntimeException("Thread not found"));
        List<Tweets> tweets= tweetRepository.findAllByThreadTweets(threadTweets);
        return tweets.stream().map(this::mapToResponseDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<TweetResponseDto> getTweetsByUsername(String username) {
        RegisteredUser registeredUser= registeredUserRepository.findByUsername(username).orElseThrow(()-> new TwitterAppRuntimeException("User not found"));
        List<Tweets> tweets= tweetRepository.findAllByUser(registeredUser);
        return tweets.stream().map(this::mapToResponseDto).collect(toList());
    }

    private Tweets mapToEntity(TweetsRequestDto tweetsRequestDto, ThreadTweets threadTweets, RegisteredUser user) {
        return Tweets.builder()
                .createdDate(Instant.now())
                .description(tweetsRequestDto.getDescription())
                .url(tweetsRequestDto.getUrl())
                .threadTweets(threadTweets)
                .tweetName(tweetsRequestDto.getTweetname())
                .user(user)
                .build();
    }

    private TweetResponseDto mapToResponseDto(Tweets tweets) {
        return TweetResponseDto.builder().id(tweets.getTweetId())
                .tweetname(tweets.getTweetName())
                .description(tweets.getDescription())
                .url(tweets.getUrl())
                .username(tweets.getUser().getUsername())
                .retweetname(tweets.getThreadTweets().getName())
                .build();

    }
}
