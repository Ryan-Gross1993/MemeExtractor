package io.kmccann.rgross.model;

import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Photo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ryan on 3/28/17.
 */
public class FacebookMemeExtractor {

    /*
    1. Dank Memes Corrupting Dreams
"

    3. Crippling Memes
    4. Spongebob Memes
    5.
     */

    final String[] pagePaths = {"975572459223141", "159444327414377",
            "1216608408354161", "141313839613346", "1097551346997380"};
    Facebook facebook = new FacebookInitializer().connect();


    public FacebookMemeExtractor() {
    }

    public String[] getPagePaths() {
        return pagePaths;
    }


    Collection<Meme> getMemesFromURI(String string) {
        return null;
    }

    public String getTimeLinePhotoId(String id) {
        for (Album album : getFacebook().mediaOperations().getAlbums(id)) {
            if (album.getName().equals("Timeline Photos")) {
                return album.getId();
            }
        }
        return "";
    }

    public List<Photo> getTodaysPhotosFromTimeLine(String id) {
        List<Photo> today = new ArrayList<>();
        for(Photo photo: getFacebook().mediaOperations().getPhotos(id)) {
            if (wasMemeMadeToday(getDateMade(photo))) {
                today.add(photo);
            }
        }
        return today;
    }



    // How do I test this? It would fail on a daily basis..
    public boolean wasMemeMadeToday(String x) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Date yesterdayDate = yesterday.getTime();

        return simpleDate.format(yesterdayDate).equals(x);
    }

    public String getTitle(Photo photo) {

        if (photo.getName() == null) {
            return "";
        } else {
            return photo.getName();
        }
    }

    public String getImageURL(Photo photo) {

        for (Photo.Image image : photo.getImages()) {
            if (image.getHeight() == 225) {
                return image.getSource();
            }
        }
        return "";
    }

    public Meme createMeme(Photo photo) {
        Meme meme = new Meme();
        meme.setTitle(getTitle(photo));
        meme.setUrl(getImageURL(photo));
        meme.setDate(getDateMade(photo));
        return meme;
    }

    public List<Meme> createMemesForOnePage(String id) {
        List<Meme> memes = new ArrayList<>();
        String timeline = getTimeLinePhotoId(id);

        for(Photo photo : getTodaysPhotosFromTimeLine(timeline)) {
            Meme meme = createMeme(photo);
            memes.add(meme);
        }
        return memes;
    }

    public List<Meme> getMemes() {
        List<Meme> memes = new ArrayList<>();
        for(String path : pagePaths) {
            memes.addAll(createMemesForOnePage(path));
        }
        return memes;
    }





    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    // http://alvinalexander.com/blog/post/java/determine-today-date
    public String getDateMade(Photo photo) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(photo.getCreatedTime());
    }



}
