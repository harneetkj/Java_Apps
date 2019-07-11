package ca.jrvs.apps.twitter.example.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class ApacheHttpHelper implements HttpHelper {

    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public ApacheHttpHelper(OAuthConsumer consumer, HttpClient httpClient) {
        this.consumer = consumer;
        this.httpClient = httpClient;
    }

    /**
     * Default constructor. Getting key and tokens from env
     */
    public ApacheHttpHelper() {
        String consumerKey = "TAsB6Ejz3G9lDk11g3hz8Srs6";
        String consumerSecret = "lTrtABLBY7S3qXYXkOmQ572oNDCvMEYQttxUHnAjK7BzrTYNyr";
        String accessToken = "3569447893-r8NYDwINFwclCJgPO4zaiOdNzqMZoaKpixWJGdp";
        String tokenSecret = "yiFaHCgrKRvNDcwIcTUB68iexCiAZGxHLd4KMkKjaAIAy";


        consumer = new CommonsHttpOAuthConsumer(consumerKey,
                consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        /**
         * Default = single connection. Discuss source code if time permit
         */
        httpClient = new DefaultHttpClient();
    }

    public ApacheHttpHelper(String consumerKey, String consumerSecret, String accessToken,
                            String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey,
                consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        /**
         * Default = single connection. Discuss source code if time permit
         */
        httpClient = new DefaultHttpClient();
    }


    @Override
    public HttpResponse HttpPost(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.POST, uri, null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    @Override
    public HttpResponse HttpPost(URI uri, StringEntity stringEntity) {
        try {
            return executeHttpRequest(HttpMethod.POST, uri, stringEntity);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    @Override
    public HttpResponse HttpGet(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.GET, uri, null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    public HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity stringEntity)
            throws OAuthException, IOException {
        if (method == HttpMethod.GET) {
            HttpGet request = new HttpGet(uri);
            consumer.sign(request);
            return httpClient.execute(request);
        } else if (method == HttpMethod.POST) {
            HttpPost request = new HttpPost(uri);
            if (stringEntity != null) {
                request.setEntity(stringEntity);
            }
            consumer.sign(request);
            return httpClient.execute(request);
        } else {
            throw new IllegalArgumentException("Unknown HTTP method: " + method.name());
        }


    }
}
