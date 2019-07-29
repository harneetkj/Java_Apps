package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TwitterRestDaoTest extends Object{

    private Tweet expectedTweet;
    private CrdDao dao;


    //executes before each test
    @Before
    public void Setup()
    {

        //setup tweet and dao
        String newTweet = " This is my test tweet" + System.currentTimeMillis();
        this.expectedTweet = new Tweet();
        expectedTweet.setText(newTweet);
        HttpHelper httpHelper = new ApacheHttpHelper();
        this.dao = new TwitterRestDao(httpHelper);

    }
    //executes after each test
    @After
    public void cleanUp(){
        //removes tweet
    }

    @Test
    public void save() throws Exception{
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(Arrays.asList(50.0, 50.0));
        coordinates.setType("Point");
        expectedTweet.setCoordinates(coordinates);
        System.out.println(JsonUtil.toPrettyJson(expectedTweet));
        // call create method

        Tweet createTweet = (Tweet) dao.save(expectedTweet);
        System.out.println(JsonUtil.toPrettyJson(createTweet));
        assertNotNull(createTweet);
        assertNotNull(expectedTweet);
        assertEquals(expectedTweet.getText(), expectedTweet.getText());

    }

    @Test
    public void findById() {
    }

    @Test
    public void deleteById() {
    }
}
