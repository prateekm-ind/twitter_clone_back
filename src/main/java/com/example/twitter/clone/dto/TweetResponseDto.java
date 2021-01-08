package com.example.twitter.clone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TweetResponseDto {
    private Long id;
    private String tweetname;
    private String url;
    private String description;
    private String username;
    private String retweetname;
}
