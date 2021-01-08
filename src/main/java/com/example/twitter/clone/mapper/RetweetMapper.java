package com.example.twitter.clone.mapper;


public interface RetweetMapper {

   /* @Mapping(target = "tweetCount", expression = "java( mapTweets(reTweets.getTweetsList())")
    ReTweetsDto mapRetweetToRetweetDto(ReTweets reTweets);

    default Integer mapTweets(List<Tweets> numberOfTweets){
        return numberOfTweets.size();
    }*/
}
