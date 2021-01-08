package com.example.twitter.clone.controller;

import com.example.twitter.clone.dto.ThreadTweetsDto;
import com.example.twitter.clone.service.ThreadtweetsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/threadtweets")
@AllArgsConstructor
public class ThreadtweetsController {
    private final ThreadtweetsService threadtweetsService;

    @GetMapping
    public ResponseEntity<List<ThreadTweetsDto>> getAllRetweets(){
        return ResponseEntity.status(HttpStatus.OK).body(threadtweetsService.getAllThreadtweets());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ThreadTweetsDto> getThreadtweet(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(threadtweetsService.getThreadtweet(id));
    }

    @PostMapping
    public ResponseEntity<ThreadTweetsDto> createThreadtweets(@RequestBody @Valid ThreadTweetsDto threadTweetsDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(threadtweetsService.createNewThreadtweet(threadTweetsDto));
    }

    @GetMapping("/name/{threadname}")
    public ResponseEntity<List<ThreadTweetsDto>> getAllThreads(@PathVariable String threadname){
        return ResponseEntity.status(HttpStatus.OK).body(threadtweetsService.getAllThreadsByName(threadname));
    }
}
