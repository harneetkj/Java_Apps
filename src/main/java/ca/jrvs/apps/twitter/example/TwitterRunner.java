package ca.jrvs.apps.twitter.example;


import ca.jrvs.apps.twitter.example.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class TwitterRunner {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private ServiceInterface service;

    @Autowired
    public TwitterRunner(ServiceInterface service) {
        this.service = service;
    }

    public void run(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("USAGE: TwitterCLIApp to post,show or delete Tweets");
        }

        switch (args[0].toLowerCase()) {
            case "post":
                postTweet(args);
                break;
            case "show":
                showTweet(args);
                break;
            case "delete":
                deleteTweet(args);
                break;
            default:
                System.out.println("USAGE: TwitterCLIApp to post,show or delete Tweets");
                break;
        }
    }

    protected void postTweet(String[] args) {
        if (args.length != 3) {
            throw new RuntimeException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        String tweet_txt = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if (coordArray.length != 2 || isEmpty(tweet_txt)) {
            throw new RuntimeException(
                    "Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        Double lat = null;
        Double lon = null;
        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"",
                    e);
        }

        service.postTweet(tweet_txt, lat, lon);
    }

    protected void showTweet(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("USAGE: TwitterCLIApp show tweet_id [fields]");
        }
        String[] fieldsArray = null;
        String tweet_id = null;

        switch (args.length) {
            case 3:
                String fields = args[2];
                if (isEmpty(fields)) {
                    throw new RuntimeException(
                            "Error: empty fields. USAGE: TwitterCLIApp show tweet_id [fields]");
                }
                fieldsArray = fields.split(COMMA);
            case 2:
                tweet_id = args[1];
                if (isEmpty(tweet_id)) {
                    throw new RuntimeException(
                            "Error: Empty ID\nUSAGE: TwitterCLIApp show tweet_id [fields]");
                }
        }

        service.showTweet(tweet_id, fieldsArray);
    }

    protected void deleteTweet(String[] args) {
        if (args.length != 2 || isEmpty(args[1])) {
            throw new RuntimeException("USAGE: TwitterCLIApp deleteTweets tweet_ids");
        }

        String tweetIds = args[1];
        String[] ids = tweetIds.split(COMMA);
        service.deleteTweets(ids);
    }
}