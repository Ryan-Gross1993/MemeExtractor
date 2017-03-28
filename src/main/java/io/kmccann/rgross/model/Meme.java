package io.kmccann.rgross.model;

/**
 * Created by ryan on 3/28/17.
 */
public class Meme {

    String title;
    String url;
    String date;

    public Meme(String title, String url, String date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public Meme(String url, String date) {
        this.url = url;
        this.date = date;
    }

    public Meme() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
