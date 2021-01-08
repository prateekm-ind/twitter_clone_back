package com.example.twitter.clone.service;

import com.example.twitter.clone.dto.ThreadTweetsDto;
import com.example.twitter.clone.entity.ThreadTweets;
import com.example.twitter.clone.exception.TwitterAppRuntimeException;
import com.example.twitter.clone.repository.ThreadtweetsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class ThreadtweetsService {

    private final ThreadtweetsRepository threadtweetsRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<ThreadTweetsDto> getAllThreadtweets() {
        return threadtweetsRepository.findAll().stream().map(this::mapToDto).collect(toList());
    }

    private ThreadTweetsDto mapToDto(ThreadTweets threadTweets) {
        return ThreadTweetsDto.builder().name(threadTweets.getName())
                .id(threadTweets.getId())
                .description(threadTweets.getDescription())
                .tweetCount(threadTweets.getTweetsList().size()).build();

    }

    @Transactional(readOnly = true)
    public ThreadTweetsDto getThreadtweet(Long id) {
       ThreadTweets reTweets= threadtweetsRepository.findById(id).orElseThrow(() -> new TwitterAppRuntimeException("Thread not found"));
       return mapToDto(reTweets);
    }

    @Transactional
    public ThreadTweetsDto createNewThreadtweet(ThreadTweetsDto threadTweetsDto) {
        threadtweetsRepository.save(mapToRetweet(threadTweetsDto));
        return threadTweetsDto;
    }

    private ThreadTweets mapToRetweet(ThreadTweetsDto threadTweetsDto) {
        return ThreadTweets.builder().name("#"+ threadTweetsDto.getName())
                    .createdDate(Instant.now())
                    .description(threadTweetsDto.getDescription())
                    .user(authService.getCurrentUser()).build();
    }

    public List<ThreadTweetsDto> getAllThreadsByName(String threadname) {
        List<ThreadTweets> threadTweets= (List<ThreadTweets>) threadtweetsRepository.findByName(threadname).orElseThrow(()-> new TwitterAppRuntimeException("Thread not found"));
        return  threadTweets.stream().map(this::mapToDto).collect(toList());
    }
}
