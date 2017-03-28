package io.kmccann.rgross.model;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.social.twitter.connect.TwitterServiceProvider;

/**
 * Created by ryan on 3/28/17.
 */
public class TwitterInitializer {

    Twitter connect() {
        TwitterConnectionFactory twitterConnectionFactory = new TwitterConnectionFactory("","");
        Twitter twitter = new TwitterServiceProvider("4w74zXy02DT6P4D4gNXpFSX8C",
                "GqzxVd4xeUyDhKmgt4hRFu7UwildkPkLnl3O9BM83ixdMRYk2D").getApi("455258922-MMLJlTDUMkfMx4bOs01f8LDg1GHYScWYRV0q6DTu",
                "C74LvC1gIF8DbbTMlKR22PwZfH2VUaCl5V7iDPmcW0paf");
        return twitter;

    }

}
