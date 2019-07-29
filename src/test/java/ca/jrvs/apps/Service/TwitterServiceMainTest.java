package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dto.Tweet;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TwitterServiceMainTest {
    //Mocking the create method of posttweet class
    @Test
    public void postTweet(){
        CrdDao mockDao = Mockito.mock(CrdDao.class);
        ServiceInterface service = new TwitterServiceMain(mockDao);
        Tweet MockTweet = new Tweet();
        MockTweet.setText("This is a test tweet");
        when(mockDao.save(any())).thenReturn(MockTweet);
        service.postTweet("tweet random",0.0 ,0.0);
        try{
            service.postTweet("",0.0,0.0);
            fail();
        }catch (IllegalArgumentException e){
            //e.printStackTrace();
        }
    }

}
