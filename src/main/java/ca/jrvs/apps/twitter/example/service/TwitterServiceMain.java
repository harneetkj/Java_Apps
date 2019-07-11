package ca.jrvs.apps.twitter.example.service;

import ca.jrvs.apps.twitter.example.dao.CrdDao;
import ca.jrvs.apps.twitter.example.dto.Tweet;
import ca.jrvs.apps.twitter.example.util.JsonUtil;
import ca.jrvs.apps.twitter.example.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import sun.security.pkcs.ParsingException;

import java.sql.SQLOutput;
import java.util.List;

import static ca.jrvs.apps.twitter.example.util.TweetUtil.buildTweet;
import static ca.jrvs.apps.twitter.example.util.TweetUtil.validatePostTweet;

public class TwitterServiceMain implements ServiceInterface {

    private CrdDao dao;

    @Autowired
    public TwitterServiceMain(CrdDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(String text, Double latitude, Double longitude) {

        //Build request tweet
        Tweet postTweet = buildTweet(text, longitude, latitude);
        //Validate Tweet (can be combined with build Tweet method
        validatePostTweet(postTweet);
        try {
            Tweet responseTweet = (Tweet) dao.save(postTweet);
            printTweet(responseTweet);
        } catch (Exception e) {
            throw new RuntimeException("Failed to post tweet");
        }
        return postTweet;
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        return null;
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        return null;
    }


    //to print the tweet
    private void printTweet(Tweet tweet) throws ParsingException, JsonProcessingException {
        System.out.println(JsonUtil.toPrettyJson(tweet));
    }
}
