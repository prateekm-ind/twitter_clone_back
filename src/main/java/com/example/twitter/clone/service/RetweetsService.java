package com.example.twitter.clone.service;

import com.example.twitter.clone.dto.ReTweetsDto;
import com.example.twitter.clone.entity.ReTweets;
import com.example.twitter.clone.exception.TwitterAppGeneralException;
import com.example.twitter.clone.repository.RetweetsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class RetweetsService {

    private final RetweetsRepository retweetsRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<ReTweetsDto> getAllRetweets() {
        return retweetsRepository.findAll().stream().map(this::mapToDto).collect(toList());
    }

    private ReTweetsDto mapToDto(ReTweets reTweets) {
        return ReTweetsDto.builder().name(reTweets.getName())
                .id(reTweets.getId())
                .tweetCount(reTweets.getTweetsList().size()).build();

    }

    @Transactional(readOnly = true)
    public ReTweetsDto getRetweet(Long id) {
       Optional<ReTweets> reTweets= Optional.ofNullable(retweetsRepository.findById(id).orElseThrow(() -> new TwitterAppGeneralException("Thread not found")));
       return mapToDto(reTweets.get());
    }

    @Transactional
    public ReTweetsDto createNewRetweet(ReTweetsDto reTweetsDto) {
        ReTweets reTweets= retweetsRepository.save(mapToRetweet(reTweetsDto));
        return mapToDto(reTweets);
    }

    private ReTweets mapToRetweet(ReTweetsDto reTweetsDto) {
        return ReTweets.builder().name("#"+reTweetsDto.getName())
                    .createdDate(Instant.now())
                    .description(reTweetsDto.getDescription())
                    .user(authService.getCurrentUser()).build();
    }
}
