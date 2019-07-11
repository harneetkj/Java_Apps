package ca.jrvs.apps.twitter.example.dao.helper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.net.URI;

public interface HttpHelper {
    HttpResponse HttpPost(URI uri);
    HttpResponse HttpPost(URI uri, StringEntity stringEntity);
    HttpResponse HttpGet(URI uri);


}
