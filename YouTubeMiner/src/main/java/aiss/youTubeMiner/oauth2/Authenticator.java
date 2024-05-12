package aiss.youTubeMiner.oauth2;

import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.oAuthModel.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Authenticator {
    @Autowired
    RestTemplate restTemplate;

    AccessToken token;

    public Authenticator() {
        System.out.println("\nOAuth 2.0 authentication is enabled.\nPlease authenticate at " + Constants.ipBase + "/login\n");
        this.token = null;
    }

    public String authRequest() {
        String uri = Constants.oauthBase;
                uri += ("?client_id=" + Constants.clientId);
                uri += ("&response_type=" + "code");
                uri += ("&scope=" + Constants.ytScope);
                uri += ("&access_type=" + "offline");
                uri += ("&redirect_uri=" + Constants.ipBase);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, null, String.class);
        return response.getBody();
    }

    public void tokenRequest(String code) {
        String uri = Constants.tokenBase;
                uri += ("?client_id=" + Constants.clientId);
                uri += ("&client_secret=" + Constants.clientSecret);
                uri += ("&code=" + code);
                uri += ("&grant_type=" + "authorization_code");
                uri += ("&redirect_uri=" + Constants.ipBase);
        token = restTemplate.exchange(uri, HttpMethod.POST, null, AccessToken.class).getBody();
    }

    public AccessToken getToken() {
        return token;
    }

    public HttpHeaders getAuthHeader() {
        HttpHeaders out = null;

        if (token != null) {
            out = new HttpHeaders() {
                {
                    set("Authorization", "Bearer " + token.getAccessToken());
                }
            };
        }
        return out;
    }
}
