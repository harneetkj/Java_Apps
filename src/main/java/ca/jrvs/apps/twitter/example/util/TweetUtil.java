package ca.jrvs.apps.twitter.example.util;

import ca.jrvs.apps.twitter.example.dto.Coordinates;
import ca.jrvs.apps.twitter.example.dto.Tweet;

import java.util.Arrays;
import java.util.function.Predicate;

import static org.springframework.util.ObjectUtils.isEmpty;

public class TweetUtil {



    //140 UTF-8 encoded characters. Each UTF-8 char is 4 bytes. Each byte = 8 bits.
    private final static Integer MAX_TWEET_CHAR = 140;

    //Validate a id
    public static Predicate<String> validId = (id) -> !isEmpty(id) && id.chars()
            .noneMatch(c -> c < '0' || c > '9');

    public static void validatePostTweet(Tweet tweet) {
        String text = tweet.getText();
        Double longitude = tweet.getCoordinates().getCoordinates().get(0);
        Double latitude = tweet.getCoordinates().getCoordinates().get(1);
        validatePostTweet(text, longitude, latitude);
    }

    public static void validatePostTweet(String text, Double longitude, Double latitude) {
        if (isEmpty(text) || text.toCharArray().length > 140) {
            throw new IllegalArgumentException("Invalid Tweet text");
        }
        if (latitude < -90d|| latitude > 90d || longitude < -180d
                || longitude > 180d) {
            throw new IllegalArgumentException("Invalid latitude or longitude value of th location");
        }
    }

    public static Tweet buildTweet(String text, Double longitude, Double latitude) {
        //validatePostTweet(text, longitude, latitude);
        Tweet tweet = new Tweet();
        tweet.setText(text);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(Arrays.asList(longitude, latitude));
        tweet.setCoordinates(coordinates);
        return tweet;
    }
}
