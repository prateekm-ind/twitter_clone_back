package com.example.twitter.clone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TweetsRequestDto {
    private Long tweetId;
    private String retweetname;
    private String tweetname;
    private String url;
    private String description;
}
