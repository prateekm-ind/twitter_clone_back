package com.example.twitter.clone.mapper;

import com.example.twitter.clone.dto.ReTweetsDto;
import com.example.twitter.clone.entity.ReTweets;
import com.example.twitter.clone.entity.Tweets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface RetweetMapper {

    @Mapping(target = "numberOfTweets", expression = "java(mapTweets(reTweets.getTweetsList())")
    ReTweetsDto mapRetweetToRetweetDto(ReTweets reTweets);

    default Integer mapTweets(List<Tweets> numberOfTweets){
        return numberOfTweets.size();
    }
}
