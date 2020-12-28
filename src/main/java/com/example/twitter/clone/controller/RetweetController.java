package com.example.twitter.clone.controller;

import com.example.twitter.clone.dto.ReTweetsDto;
import com.example.twitter.clone.entity.ReTweets;
import com.example.twitter.clone.repository.RetweetsRepository;
import com.example.twitter.clone.service.RetweetsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/retweets")
@AllArgsConstructor
public class RetweetController {
    private final RetweetsService retweetsService;

    @GetMapping
    public List<ReTweetsDto> getAllRetweets(){
        return retweetsService.getAllRetweets();
    }

    @GetMapping("/{id}")
    public ReTweetsDto getRetweet(@PathVariable Long id){
        return retweetsService.getRetweet(id);
    }

    @PostMapping
    public ReTweetsDto createRetweet(@RequestBody @Valid ReTweetsDto reTweetsDto){
        return retweetsService.createNewRetweet(reTweetsDto);
    }
}
