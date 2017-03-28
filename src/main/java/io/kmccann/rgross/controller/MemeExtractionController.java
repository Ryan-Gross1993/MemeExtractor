package io.kmccann.rgross.controller;



import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.api.client.util.Key;
import com.google.api.client.util.store.DataStore;
import com.google.auth.oauth2.OAuth2Credentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.kmccann.rgross.model.FacebookMemeExtractor;
import io.kmccann.rgross.model.ImageExtractor;
import io.kmccann.rgross.model.Meme;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 3/28/17.
 */
@RestController
public class MemeExtractionController {


    @RequestMapping(path="getMemes", method = RequestMethod.GET, produces = "application/json")
    public void generateDailyMemes() throws Exception {
        FileInputStream serviceAccount =
                new FileInputStream("/Users/ryan/dev/uandmemelogin/src/main/java/rgross/kmccann/firebase.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl("https://uandmeme-57321.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

//        Storage storage = StorageOptions.getDefaultInstance().getService();

        ImageExtractor imageExtractor = new ImageExtractor();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        List<Meme> memes = new FacebookMemeExtractor().getMemes();
        DatabaseReference databaseReference = firebaseDatabase.getReference("/dailyMemes");


        //  databaseReference.setValue(new FacebookMemeExtractor().getMemes());
        /*
        for (Meme meme : memes) {
            InputStream inputStream = new URL(meme.getUrl()).openStream();
            BlobId blobId = BlobId.of("", meme.getUrl());
            BlobInfo blobInfo = BlobInfo.builder(blobId).contentType("image/jpg").build();
            storage.create(blobInfo, inputStream);

        }

        for (Meme meme : memes) {
            InputStream inputStream = new URL(meme.getUrl()).openStream();
            BlobId blobId = BlobId.of("", meme.getUrl());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpg").build();
           // storage.create(blobId, inputStream);
            storage.create(blobInfo, inputStream);

        }
        */
        // need to cut the URL down so this can work properly..
        for (Meme meme : memes) {
            InputStream inputStream = new URL(meme.getUrl()).openStream();
            AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIGFMMOUJEAJDINPQ", "LGo7uvd+1Dk1LgyjLQ433/S3BtegZd0UTC2TkDts");
            AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");
            amazonS3.putObject("uandmeme", meme.getUrl(),inputStream, objectMetadata);
        }
    }

}
