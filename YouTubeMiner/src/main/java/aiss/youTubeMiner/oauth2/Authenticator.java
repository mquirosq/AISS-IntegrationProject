package aiss.youTubeMiner.oauth2;

import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.helper.ConstantsHelper;
import aiss.youTubeMiner.oAuthModel.AccessToken;
import aiss.youTubeMiner.oAuthModel.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

public class Authenticator {
    @Autowired
    RestTemplate restTemplate;

    AccessToken token;
    LocalDateTime lastRefreshTime;

    public Authenticator() {
        System.out.println("\nOAuth 2.0 authentication is enabled, please log in at: " + ConstantsHelper.ipBase + "/login\n");
        this.token = null;
        this.lastRefreshTime = null;
    }

    public String authRequest() {
        String uri = ConstantsHelper.oauthBase;
                uri += ("?client_id=" + ConstantsHelper.clientId);
                uri += ("&response_type=" + "code");
                uri += ("&scope=" + ConstantsHelper.ytScope);
                uri += ("&access_type=" + "offline");
                uri += ("&redirect_uri=" + ConstantsHelper.ipBase);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, null, String.class);
        return response.getBody();
    }

    public void tokenRequest(String code) {
        String uri = ConstantsHelper.tokenBase + "/token";
                uri += ("?client_id=" + ConstantsHelper.clientId);
                uri += ("&client_secret=" + ConstantsHelper.clientSecret);
                uri += ("&code=" + code);
                uri += ("&grant_type=" + "authorization_code");
                uri += ("&redirect_uri=" + ConstantsHelper.ipBase);
        token = restTemplate.exchange(uri, HttpMethod.POST, null, AccessToken.class).getBody();
        lastRefreshTime = LocalDateTime.now();

        System.out.println("\nSuccessfully logged in.");
        System.out.println("You may log out here: " + ConstantsHelper.ipBase + "/logout\n");
    }

    public String revokeRequest() {
        String out = "";

        if (token == null) {
            out = "User is already logged out.";
            System.out.println("\n" + out + "\n");
            return out;
        }
        String uri = ConstantsHelper.tokenBase + "/revoke";
                uri += ("?token=" + token.getAccessToken());
        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, null, String.class);
            token = null;
            out = "Successfully logged out.";
        } catch (RestClientResponseException e) {
            out = "An error occurred during the logout process: " + e.getLocalizedMessage();
        }
        System.out.println("\n" + out + "\n");
        return out;
    }

    public void refreshRequest() {
        String uri = ConstantsHelper.tokenBase + "/token";
                uri += ("?client_id=" + ConstantsHelper.clientId);
                uri += ("&client_secret=" + ConstantsHelper.clientSecret);
                uri += ("&refresh_token=" + token.getRefreshToken());
                uri += ("&grant_type=" + "refresh_token");
        ResponseEntity<RefreshToken> response = restTemplate.exchange(uri, HttpMethod.POST, null, RefreshToken.class);
        token.setAccessToken(response.getBody().getAccessToken());
        token.setExpiresIn(response.getBody().getExpiresIn());
        lastRefreshTime = LocalDateTime.now();

        System.out.println("\nToken refreshed.\n");
    }

    public AccessToken getToken() {
        return token;
    }

    public HttpHeaders getAuthHeader() throws OAuthException {
        HttpHeaders out = null;

        if (token != null) {
            if (LocalDateTime.now().isAfter(lastRefreshTime.plusSeconds(token.getExpiresIn()))) {
                refreshRequest();
            }
            out = new HttpHeaders() {
                {
                    set("Authorization", "Bearer " + token.getAccessToken());
                }
            };
        } else {
            throw new OAuthException();
        }
        return out;
    }
}
