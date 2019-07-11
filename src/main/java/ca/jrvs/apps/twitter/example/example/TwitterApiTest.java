package ca.jrvs.apps.twitter.example.example;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;

import static java.lang.System.out;

public class TwitterApiTest {
    private static String CONSUMER_KEY = "TAsB6Ejz3G9lDk11g3hz8Srs6";
    private static String CONSUMER_SECRET = "lTrtABLBY7S3qXYXkOmQ572oNDCvMEYQttxUHnAjK7BzrTYNyr";
    private static String ACCESS_TOKEN = "3569447893-r8NYDwINFwclCJgPO4zaiOdNzqMZoaKpixWJGdp";
    private static String TOKEN_SECRET = "yiFaHCgrKRvNDcwIcTUB68iexCiAZGxHLd4KMkKjaAIAy";

    public static void main(String[] args) throws Exception {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN,TOKEN_SECRET);

        HttpGet request = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=realDonaldTrump");
        consumer.sign(request);

        out.println("HTTP request headers");
        Arrays.stream(request.getAllHeaders()).forEach(out::println);
        HttpClient httpClient = new DefaultHttpClient();


        HttpResponse response = httpClient.execute(request);


        System.out.println(EntityUtils.toString(response.getEntity()));



    }
}
