package ca.jrvs.apps.twitter.example.dao;

import ca.jrvs.apps.twitter.example.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.dto.Tweet;
import ca.jrvs.apps.twitter.example.util.JsonUtil;
import org.apache.http.*;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Repository
public class TwitterRestDao implements CrdDao <Tweet, String> {
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";



    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterRestDao(HttpHelper httpHelper){
        this.httpHelper = httpHelper;
    }

    @Override
    //constructing uri TO POST
    public Tweet save(Tweet tweet) {
        URI uri;
        try {
            uri = getPostUri(tweet);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invalid tweet entered", e);
        }
    //execute request
        HttpResponse response;
        response = httpHelper.HttpPost(uri);

    return parseResponseBody(response, HTTP_OK);
        }



    @Override
    public Tweet findById(Tweet Entity) {
        URI uri;

        return null;
    }

    @Override
    public Tweet deleteById(String s) {
        return null;
    }

    protected URI getPostUri(Tweet tweet) throws URISyntaxException , UnsupportedEncodingException
    {
        String tweet_text = tweet.getText();
        Double longitude = tweet.getCoordinates().getCoordinates().get(0);
        Double latitude = tweet.getCoordinates().getCoordinates().get(1);
        StringBuilder sb = new StringBuilder();
        sb.append(API_BASE_URI)
                .append(POST_PATH)
                .append(QUERY_SYM);

        appendQueryParam(sb, "status", URLEncoder.encode(tweet_text, StandardCharsets.UTF_8.name()), true);
        appendQueryParam(sb, "long", longitude.toString(), false);
        appendQueryParam(sb, "lat", latitude.toString(), false);

        return new URI(sb.toString());

    }
    private void appendQueryParam(StringBuilder sb, String key, String value, boolean param){
        if(!param)
        {
            sb.append(AMPERSAND);
        }
        sb.append(key)
                .append(EQUAL)
                .append(value);
    }
protected Tweet parseResponseBody(HttpResponse response, int code)
{
    Tweet tweet = null;
    int Status_code = response.getStatusLine().getStatusCode();
    if (code != Status_code)
    {
        try {
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            System.out.println("Response includes no entity");
        }
        throw new RuntimeException("Unexpected status:" + Status_code);
    }

    if (response.getEntity() == null) {
        throw new RuntimeException("Empty response body");
    }

    //Convert Response we got from twitter to JSON string
    String jsonStr;
    try {
        jsonStr = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
        throw new RuntimeException("Failed to convert entity to String", e);
    }

    //Convert JSON string to Tweet object
    try {
        tweet = (Tweet) JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
    } catch (IOException e) {
        throw new RuntimeException("Unable to convert JSON str to Object", e);
    }

    return tweet;
}
    }

