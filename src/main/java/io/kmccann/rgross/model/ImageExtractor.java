package io.kmccann.rgross.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ryan on 3/28/17.
 */
public class ImageExtractor {

    public Image generateImagefromMeme(Meme meme) throws IOException {
        Image memeImage = null;


        try {
            URL url = new URL(meme.getUrl());
            memeImage = ImageIO.read(url);
        } catch (IOException e) {

        }
        return memeImage;
    }

    ArrayList<Image> generateImages(ArrayList<Meme> memes) throws IOException {
        ArrayList<Image> memeImages = new ArrayList<>();
        for (Meme meme : memes) {
            memeImages.add(generateImagefromMeme(meme));
        }
        return memeImages;
    }


}
