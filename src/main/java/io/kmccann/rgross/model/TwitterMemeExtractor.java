package io.kmccann.rgross.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.social.twitter.api.*;

/**
 * Created by ryan on 3/28/17.
 */
public class TwitterMemeExtractor {

    final String[] twitterHandles = {"@MemesOnHistory", "@Hoodmemes", "@ZestiMemes", "@HilariousEdited"};
    Twitter twitter = new TwitterInitializer().connect();

    // Can this be put into the interface?
    public boolean wasMemeMadeToday(String tweetTime)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Date yesterdayDate = yesterday.getTime();
        return simpleDateFormat.format(yesterdayDate).equals(tweetTime);
    }


    public String getDateMade(Tweet tweet) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(tweet.getCreatedAt());
    }

    public String getUrl(Tweet tweet) {
        if (tweet.hasMedia()) {
            for(MediaEntity mediaEntity : tweet.getEntities().getMedia()) {
                if (mediaEntity.getType().equals("photo")) {
                    return mediaEntity.getMediaSecureUrl();
                }
            }
        }
        return "";
    }

    public String extractTitle(String textFromTweet) {
        String test = textFromTweet.replaceAll("http\\S+", "");
        return test;
    }

    // Get all current Tweets

    public List<Tweet> getTweetsFromToday(String handle) {
        List<Tweet> todaysTweets = new ArrayList<>();
        SearchResults searchResults = twitter.searchOperations().search(handle);
        for(Tweet tweet : searchResults.getTweets()) {
            if (wasMemeMadeToday(getDateMade(tweet))) {
                todaysTweets.add(tweet);
            }
        }
        return todaysTweets;
    }

    public Meme createMeme(Tweet tweet) {
        Meme meme = new Meme();
        meme.setTitle(extractTitle(tweet.getUnmodifiedText()));
        meme.setDate(getDateMade(tweet));
        meme.setUrl(getUrl(tweet));
        return meme;
    }

    public List<Meme> getMemesFromOneHandle(String handle) {
        List<Meme> memes = new ArrayList<>();

        for (Tweet tweet : getTweetsFromToday(handle)) {
            memes.add(createMeme(tweet));

        }
        return memes;
    }

    public List<Meme> removeDuplicates(List<Meme> memes) {

        for (int i = 0; i < memes.size(); i++) {
            for (int j = 0; j < memes.size(); j++) {
                if ((i != j) && (memes.get(i).getDate().equals(memes.get(j).getDate())) &&
                        (memes.get(i).getUrl().equals(memes.get(j).getUrl())) &&
                        (memes.get(i).getTitle().equals(memes.get(j).getTitle())) ) {
                    memes.remove(i);
                }
            }
        }
        return memes;
    }

    public List<Meme> getMemes() {
        List<Meme> allMemes = new ArrayList<>();
        for (String handle : twitterHandles) {
            allMemes.addAll(getMemesFromOneHandle(handle));
        }
        return removeDuplicates(allMemes);
    }




}
