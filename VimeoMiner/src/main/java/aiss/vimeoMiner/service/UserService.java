package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.UserNotFoundException;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import aiss.vimeoMiner.vimeoModel.modelUser.ModelUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    RestTemplate restTemplate;

    // Get from Vimeo API
    public ModelUser getUser(String userUri) throws UserNotFoundException {
        String uri = "https://api.vimeo.com" + userUri;

        HttpHeaders header = new HttpHeaders(){
            {
                String auth = "Bearer ee507ffdb4da956d56252e8eb067fb58";
                set("Authorization", auth);
            }
        };

        try {
            ResponseEntity<ModelUser> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<ModelUser>(header),ModelUser.class);
            ModelUser user = response.getBody();
            return user;
        }
        catch (RestClientResponseException err) {
            throw new UserNotFoundException();
        }
    }

    public ModelUser postUser(ModelUser user) {
        String uri = "https://api.vimeo.com/users/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ee507ffdb4da956d56252e8eb067fb58");

        try {
            HttpEntity<ModelUser> request = new HttpEntity<>(user, headers);
            ResponseEntity<ModelUser> response = restTemplate.exchange(uri, HttpMethod.POST, request, ModelUser.class);
            return response.getBody();
        } catch (RestClientResponseException ex) {
            // Handle exception appropriately
            ex.printStackTrace();
            return null;
        }
    }
}


