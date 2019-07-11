package ca.jrvs.apps.twitter.example;

import ca.jrvs.apps.twitter.example.dao.CrdDao;
import ca.jrvs.apps.twitter.example.dao.TwitterRestDao;
import ca.jrvs.apps.twitter.example.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.example.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.service.ServiceInterface;
import ca.jrvs.apps.twitter.example.service.TwitterServiceMain;

public class TwitterMainApp {
    public static void main(String[] args) {
        HttpHelper httpHelper = new ApacheHttpHelper();
        CrdDao dao = new TwitterRestDao(httpHelper);
        ServiceInterface service = new TwitterServiceMain(dao);

        //Create Runner
        TwitterRunner runner = new TwitterRunner(service);

        //Run Application
        runner.run(args);
    }
}