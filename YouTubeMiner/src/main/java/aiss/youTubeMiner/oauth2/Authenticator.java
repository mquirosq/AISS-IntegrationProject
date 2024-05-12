package aiss.youTubeMiner.oauth2;

import aiss.youTubeMiner.helper.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Authenticator {
    @Autowired
    RestTemplate restTemplate;

    String token;

    public Authenticator() {
        System.out.println("\nOAuth 2.0 authentication is enabled.\nPlease authenticate at " + Constants.loginBase + "\n");
        this.token = null;
    }

    public String authRequest() {
        String uri = "https://accounts.google.com/o/oauth2/v2/auth";
                uri += ("?client_id=" + Constants.clientId);
                uri += ("&response_type=" + "code");
                uri += ("&scope=" + "https://www.googleapis.com/auth/youtube");
                uri += ("&redirect_uri=" + "http://localhost:8082");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, null, String.class);
        return response.getBody();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
