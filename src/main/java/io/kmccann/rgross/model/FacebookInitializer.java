package io.kmccann.rgross.model;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * Created by ryan on 3/28/17.
 */
public class FacebookInitializer {

    Facebook connect() {

        // Token ID/Password need to be hidden!!
        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory("277226759358384",
                "0f4078dec7af84402750ac7e853d6f52");
        OAuth2Operations oAuth2Operations = facebookConnectionFactory.getOAuthOperations();
        AccessGrant accessGrant = oAuth2Operations.authenticateClient();
        return new FacebookTemplate(accessGrant.getAccessToken());
        // Refreshing the Token code should be done in exception handling. FB mentions how this is done automatically..
    }

}
